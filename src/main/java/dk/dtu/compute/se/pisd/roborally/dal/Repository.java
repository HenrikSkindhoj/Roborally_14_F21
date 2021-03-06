/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.dal;

import dk.dtu.compute.se.pisd.roborally.controller.AppController;
import dk.dtu.compute.se.pisd.roborally.fileaccess.LoadBoard;
import dk.dtu.compute.se.pisd.roborally.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
class Repository implements IRepository {

    private static final String GAME_GAMEID = "gameID";

    private static final String GAME_NAME = "name";

    private static final String GAME_CURRENTPLAYER = "currentPlayer";

    private static final String GAME_PHASE = "phase";

    private static final String GAME_STEP = "step";

    private static final String PLAYER_PLAYERID = "playerID";

    private static final String PLAYER_NAME = "name";

    private static final String PLAYER_COLOUR = "colour";

    private static final String PLAYER_GAMEID = "gameID";

    private static final String PLAYER_POSITION_X = "positionX";

    private static final String PLAYER_POSITION_Y = "positionY";

    private static final String PLAYER_HEADING = "heading";

    private Connector connector;

    private ArrayList<Laser> lasers = new ArrayList<>();

    private ArrayList<Wall> walls = new ArrayList<>();

    Repository(Connector connector){
        this.connector = connector;
    }

    /** {@inheritDoc} */
    @Override
    public boolean createGameInDB(Board game) {
        if (game.getGameId() == null) {
            Connection connection = connector.getConnection();
            try {
                connection.setAutoCommit(false);

                PreparedStatement ps = getInsertGameStatementRGK();
                // TODO: the name should eventually set by the user
                //       for the game and should be then used
                //       game.getName();
                ps.setString(1, "Date: " +  new Date()); // instead of name
                ps.setNull(2, Types.TINYINT); // game.getPlayerNumber(game.getCurrentPlayer())); is inserted after players!
                ps.setInt(3, game.getPhase().ordinal());
                ps.setInt(4, game.getStep());
                ps.setString(5,game.boardName);

                // If you have a foreign key constraint for current players,
                // the check would need to be temporarily disabled, since
                // MySQL does not have a per transaction validation, but
                // validates on a per row basis.
                // Statement statement = connection.createStatement();
                // statement.execute("SET foreign_key_checks = 0");

                int affectedRows = ps.executeUpdate();
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (affectedRows == 1 && generatedKeys.next()) {
                    game.setGameId(generatedKeys.getInt(1));
                }
                generatedKeys.close();

                // Enable foreign key constraint check again:
                // statement.execute("SET foreign_key_checks = 1");
                // statement.close();

                createPlayersInDB(game);
                createLasersInDB(game);
                createWallsInDB(game);
                createCheckpointInDB(game);
				createCardFieldsInDB(game);

                // since current player is a foreign key, it can oly be
                // inserted after the players are created, since MySQL does
                // not have a per transaction validation, but validates on
                // a per row basis.
                ps = getSelectGameStatementU();
                ps.setInt(1, game.getGameId());

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    rs.updateInt(GAME_CURRENTPLAYER, game.getPlayerNumber(game.getCurrentPlayer()));
                    rs.updateRow();
                } else {
                    // TODO error handling
                }
                rs.close();

                connection.commit();
                connection.setAutoCommit(true);
                return true;
            } catch (SQLException e) {
                // TODO error handling
                e.printStackTrace();
                System.err.println("Some DB error");

                try {
                    connection.rollback();
                    connection.setAutoCommit(true);
                } catch (SQLException e1) {
                    // TODO error handling
                    e1.printStackTrace();
                }
            }
        } else {
            System.err.println("Game cannot be created in DB, since it has a game id already!");
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean updateGameInDB(Board game) {
        assert game.getGameId() != null;

        Connection connection = connector.getConnection();
        try {
            connection.setAutoCommit(false);

            PreparedStatement ps = getSelectGameStatementU();
            ps.setInt(1, game.getGameId());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rs.updateInt(GAME_CURRENTPLAYER, game.getPlayerNumber(game.getCurrentPlayer()));
                rs.updateInt(GAME_PHASE, game.getPhase().ordinal());
                rs.updateInt(GAME_STEP, game.getStep());
                rs.updateRow();
            } else {
                // TODO error handling
            }
            rs.close();

            updatePlayersInDB(game);
			/* TODO this method needs to be implemented first
			updateCardFieldsInDB(game);
			*/

            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            // TODO error handling
            e.printStackTrace();
            System.err.println("Some DB error");

            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                // TODO error handling
                e1.printStackTrace();
            }
        }

        return false;
    }

    /** {@inheritDoc} */
    @Override
    public Board loadGameFromDB(int id) {
        Board game;
        try {
            // TODO here, we could actually use a simpler statement
            //      which is not updatable, but reuse the one from
            //      above for the pupose
            PreparedStatement ps = getSelectGameStatementU();
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            int playerNo = -1;
            if (rs.next()) {
                // TODO the width and height could eventually come from the database
                // int width = AppController.BOARD_WIDTH;
                // int height = AppController.BOARD_HEIGHT;
                // game = new Board(width,height);
                // TODO and we should also store the used game board in the database
                //      for now, we use the default game board
                game = LoadBoard.loadBoard(rs.getString("boardName"));
                if (game == null) {
                    return null;
                }
                playerNo = rs.getInt(GAME_CURRENTPLAYER);
                // TODO currently we do not set the games name (needs to be added)
                game.setPhase(Phase.values()[rs.getInt(GAME_PHASE)]);
                game.setStep(rs.getInt(GAME_STEP));
            } else {
                // TODO error handling
                return null;
            }
            rs.close();


            game.setGameId(id);
            loadPlayersFromDB(game);
            game.setCheckpoints(new Checkpoints(game));
            loadWallsFromDB(game);
            loadLasersFromDB(game);
            for(Laser laser : lasers)
            {
                laser.setEndSpace();
            }
            loadCheckpointFromDB(game);

            if (playerNo >= 0 && playerNo < game.getPlayersNumber()) {
                game.setCurrentPlayer(game.getPlayer(playerNo));
            } else {
                // TODO  error handling
                return null;
            }


            loadCardsFromDB(game);


            return game;
        } catch (SQLException e) {
            // TODO error handling
            e.printStackTrace();
            System.err.println("Some DB error");
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public List<GameInDB> getGames() {
        // TODO when there many games in the DB, fetching all available games
        //      from the DB is a bit extreme; eventually there should a
        //      methods that can filter the returned games in order to
        //      reduce the number of the returned games.
        List<GameInDB> result = new ArrayList<>();
        try {
            PreparedStatement ps = getSelectGameIdsStatement();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(GAME_GAMEID);
                String name = rs.getString(GAME_NAME);
                result.add(new GameInDB(id,name));
            }
            rs.close();
        } catch (SQLException e) {
            // TODO proper error handling
            e.printStackTrace();
        }
        return result;
    }

    private void createPlayersInDB(Board game) throws SQLException {
        // TODO code should be more defensive
        PreparedStatement ps = getSelectPlayersStatementU();
        ps.setInt(1, game.getGameId());

        ResultSet rs = ps.executeQuery();
        for (int i = 0; i < game.getPlayersNumber(); i++) {
            Player player = game.getPlayer(i);
            rs.moveToInsertRow();
            rs.updateInt(PLAYER_GAMEID, game.getGameId());
            rs.updateInt(PLAYER_PLAYERID, i);
            rs.updateString(PLAYER_NAME, player.getName());
            rs.updateString(PLAYER_COLOUR, player.getColor());
            rs.updateInt(PLAYER_POSITION_X, player.getSpace().x);
            rs.updateInt(PLAYER_POSITION_Y, player.getSpace().y);
            rs.updateInt(PLAYER_HEADING, player.getHeading().ordinal());
            rs.insertRow();
        }

        rs.close();
    }

    private void createLasersInDB(Board game) throws SQLException
    {
        PreparedStatement ps = getSelectLaserStatement();
        ps.setInt(1, game.getGameId());

        lasers = game.getLasers();
        ResultSet rs = ps.executeQuery();
        for( int i = 0; i < lasers.size(); i++)
        {
            Laser laser = lasers.get(i);
            rs.moveToInsertRow();
            rs.updateInt(GAME_GAMEID,game.getGameId());
            rs.updateInt("laserID",laser.getId());
            rs.updateInt(PLAYER_POSITION_X, laser.getStartSpace().x);
            rs.updateInt(PLAYER_POSITION_Y, laser.getStartSpace().y);
            rs.updateInt(PLAYER_HEADING, laser.getOrdinal());
            rs.insertRow();
        }
        rs.close();
    }

    private void createWallsInDB(Board game) throws SQLException
    {
        PreparedStatement ps = getSelectWallStatement();
        ps.setInt(1, game.getGameId());

        walls = game.getWalls();
        ResultSet rs = ps.executeQuery();
        for( int i = 0; i < walls.size(); i++)
        {
            Wall wall = walls.get(i);
            rs.moveToInsertRow();
            rs.updateInt(GAME_GAMEID,game.getGameId());
            rs.updateInt("wallID",wall.getId());
            rs.updateInt(PLAYER_POSITION_X, wall.getX());
            rs.updateInt(PLAYER_POSITION_Y, wall.getY());
            rs.updateInt(PLAYER_HEADING, wall.getOrdinal());
            rs.insertRow();
        }
        rs.close();
    }

    private void createCheckpointInDB(Board game) throws SQLException
    {
        PreparedStatement ps = getSelectCheckpointStatement();
        ps.setInt(1, game.getGameId());

        ResultSet rs = ps.executeQuery();
        for( int i = 0; i < game.getCheckpoints().getCheckpoints().size(); i++)
        {
            Checkpoint checkpoint = game.getCheckpoints().getCheckpoints().get(i);
            rs.moveToInsertRow();
            rs.updateInt(GAME_GAMEID,game.getGameId());
            rs.updateInt("checkpointID",checkpoint.getId());
            rs.updateInt(PLAYER_POSITION_X, checkpoint.getX());
            rs.updateInt(PLAYER_POSITION_Y, checkpoint.getY());
            rs.insertRow();
        }
        rs.close();
    }

    private void createCardFieldsInDB(Board game) throws SQLException
    {
        PreparedStatement ps = getSelectCardsStatement();
        ps.setInt(1,game.getGameId());

        ResultSet rs = ps.executeQuery();
        for( int i = 0; i < game.getPlayers().size(); i++)
        {

            Player player = game.getPlayer(i);

            rs.moveToInsertRow();
            rs.updateInt(GAME_GAMEID,game.getGameId());
            rs.updateInt("playerID",i);
            for(int x = 0; x < 5; x++)
            {
                if(!valueNullChecker(player.getProgramField(x).getCard()))
                    rs.updateString("pCard"+(x+1), String.valueOf(player.getProgramField(x).getCard().command));
                else rs.updateNull("pCard"+(x+1));
            }

            for(int x = 0; x < 8; x++)
            {
                if(!valueNullChecker(player.getCardField(x).getCard()))
                    rs.updateString("cCard"+(x+1), String.valueOf(player.getCardField(x).getCard().command));
                else rs.updateNull("cCard"+(x+1));
            }
            rs.insertRow();
        }
        rs.close();
    }

    private void loadPlayersFromDB(Board game) throws SQLException {
        PreparedStatement ps = getSelectPlayersASCStatement();
        ps.setInt(1, game.getGameId());

        ResultSet rs = ps.executeQuery();
        int i = 0;
        while (rs.next()) {
            int playerId = rs.getInt(PLAYER_PLAYERID);
            if (i++ == playerId) {
                // TODO this should be more defensive
                String name = rs.getString(PLAYER_NAME);
                String colour = rs.getString(PLAYER_COLOUR);
                Player player = new Player(game, colour ,name);
                game.addPlayer(player);

                int x = rs.getInt(PLAYER_POSITION_X);
                int y = rs.getInt(PLAYER_POSITION_Y);
                player.setSpace(game.getSpace(x,y));
                int heading = rs.getInt(PLAYER_HEADING);
                player.setHeading(Heading.values()[heading]);

                // TODO  should also load players program and hand here
            } else {
                // TODO error handling
                System.err.println("Game in DB does not have a player with id " + i +"!");
            }
        }
        rs.close();
    }

    private void loadLasersFromDB(Board game) throws SQLException
    {
        PreparedStatement ps = getSelectLasersASCStatement();
        ps.setInt(1, game.getGameId());

        ResultSet rs = ps.executeQuery();
        int i = 1;
        while (rs.next()) {
            int laserId = rs.getInt("laserID");
            if (i++ == laserId) {
                // TODO this should be more defensive
                int laserX = rs.getInt("positionX");
                int laserY = rs.getInt("positionY");
                int laserHeading = rs.getInt("heading");
                Laser laser = new Laser(laserId,laserX,laserY,Heading.values()[laserHeading]);
                laser.setStartSpace(game.getSpace(laserX,laserY));
                game.addLaser(laser);
            } else {
                // TODO error handling
                System.err.println("Game in DB does not have a laser with id " + i +"!");
            }
        }
        rs.close();
    }

    private void loadWallsFromDB(Board game) throws SQLException
    {
        PreparedStatement ps = getSelectWallsASCStatement();
        ps.setInt(1, game.getGameId());

        ResultSet rs = ps.executeQuery();
        int i = 1;
        while (rs.next()) {
            int wallID = rs.getInt("wallID");
            if (i++ == wallID) {
                // TODO this should be more defensive
                int wallX = rs.getInt("positionX");
                int wallY = rs.getInt("positionY");
                int wallHeading = rs.getInt("heading");
                Wall wall = new Wall(wallID,wallX,wallY,Heading.values()[wallHeading]);
                game.addWall(wall);
            } else {
                // TODO error handling
                System.err.println("Game in DB does not have a wall with id " + i +"!");
            }
        }
        rs.close();
    }

    private void loadCheckpointFromDB(Board game) throws SQLException
    {
        PreparedStatement ps = getSelectCheckpointASCStatement();
        ps.setInt(1, game.getGameId());

        ResultSet rs = ps.executeQuery();
        int i = 1;
        while (rs.next()) {
            int checkpointID = rs.getInt("checkpointID");
            if (i++ == checkpointID) {
                // TODO this should be more defensive
                int checkpointX = rs.getInt("positionX");
                int checkpointY = rs.getInt("positionY");
                Checkpoint checkpoint = new Checkpoint(checkpointX,checkpointY,checkpointID);
                game.addCheckpoint(checkpoint);
            } else {
                // TODO error handling
                System.err.println("Game in DB does not have a checkpoint with id " + i +"!");
            }
        }
        rs.close();
    }

    private void loadCardsFromDB(Board game) throws SQLException
    {
        PreparedStatement ps = getSelectCardASCStatement();
        ps.setInt(1,game.getGameId());

        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            int playerID = rs.getInt("playerID");

            for(int i = 0; i < 5; i++)
            {
                String cardName = rs.getString("pCard"+(i+1));
                if(cardName != null)
                game.getPlayer(playerID).getProgramField(i).setCard(
                        new CommandCard(Command.valueOf(cardName)));
            }

            for(int i = 0; i < 8; i++)
            {
                String cardName = rs.getString("cCard"+(i+1));
                if(cardName != null)
                game.getPlayer(playerID).getCardField(i).setCard(
                        new CommandCard(Command.valueOf(rs.getString("cCard"+(i+1)))));
            }

        }
        rs.close();
    }

    private void updatePlayersInDB(Board game) throws SQLException {
        PreparedStatement ps = getSelectPlayersStatementU();
        ps.setInt(1, game.getGameId());

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int playerId = rs.getInt(PLAYER_PLAYERID);
            // TODO should be more defensive
            Player player = game.getPlayer(playerId);
            // rs.updateString(PLAYER_NAME, player.getName()); // not needed: player's names does not change
            rs.updateInt(PLAYER_POSITION_X, player.getSpace().x);
            rs.updateInt(PLAYER_POSITION_Y, player.getSpace().y);
            rs.updateInt(PLAYER_HEADING, player.getHeading().ordinal());
            // TODO error handling
            // TODO take care of case when number of players changes, etc
            rs.updateRow();
        }
        rs.close();

        // TODO error handling/consistency check: check whether all players were updated
    }

    private static final String SQL_INSERT_GAME =
            "INSERT INTO Game(name, currentPlayer, phase, step, boardName) VALUES (?, ?, ?, ?, ?)";

    private PreparedStatement insert_game_stmt = null;

    private PreparedStatement getInsertGameStatementRGK() {
        if (insert_game_stmt == null) {
            Connection connection = connector.getConnection();
            try {
                insert_game_stmt = connection.prepareStatement(
                        SQL_INSERT_GAME,
                        Statement.RETURN_GENERATED_KEYS);
            } catch (SQLException e) {
                // TODO error handling
                e.printStackTrace();
            }
        }
        return insert_game_stmt;
    }

    private static final String SQL_SELECT_GAME =
            "SELECT * FROM Game WHERE gameID = ?";

    private PreparedStatement select_game_stmt = null;

    private PreparedStatement getSelectGameStatementU() {
        if (select_game_stmt == null) {
            Connection connection = connector.getConnection();
            try {
                select_game_stmt = connection.prepareStatement(
                        SQL_SELECT_GAME,
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE);
            } catch (SQLException e) {
                // TODO error handling
                e.printStackTrace();
            }
        }
        return select_game_stmt;
    }

    private static final String SQL_SELECT_PLAYERS =
            "SELECT * FROM Player WHERE gameID = ?";

    private PreparedStatement select_players_stmt = null;

    private PreparedStatement getSelectPlayersStatementU() {
        if (select_players_stmt == null) {
            Connection connection = connector.getConnection();
            try {
                select_players_stmt = connection.prepareStatement(
                        SQL_SELECT_PLAYERS,
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE);
            } catch (SQLException e) {
                // TODO error handling
                e.printStackTrace();
            }
        }
        return select_players_stmt;
    }

    private static final String SQL_SELECT_LASERS =
            "SELECT * FROM Laser WHERE gameID = ?";

    private PreparedStatement select_lasers_stmt = null;

    private PreparedStatement getSelectLaserStatement()
    {
        Connection connection = connector.getConnection();
        try
        {
            select_lasers_stmt = connection.prepareStatement(
                    SQL_SELECT_LASERS,
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return  select_lasers_stmt;
    }

    private static final String SQL_SELECT_WALLS =
            "SELECT * FROM Wall WHERE gameID = ?";

    private PreparedStatement select_walls_stmt = null;

    private PreparedStatement getSelectWallStatement()
    {
        Connection connection = connector.getConnection();
        try
        {
            select_walls_stmt = connection.prepareStatement(
                    SQL_SELECT_WALLS,
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return  select_walls_stmt;
    }

    private static final String SQL_SELECT_CHECKPOINT =
            "SELECT * FROM Checkpoint WHERE gameID = ?";

    private PreparedStatement select_checkpoints_stmt = null;

    private PreparedStatement getSelectCheckpointStatement()
    {
        Connection connection = connector.getConnection();
        try
        {
            select_checkpoints_stmt = connection.prepareStatement(
                    SQL_SELECT_CHECKPOINT,
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return  select_checkpoints_stmt;
    }

    private static final String SQL_SELECT_CARD =
            "SELECT * FROM Register WHERE gameID = ?";

    private PreparedStatement select_cards_stmt = null;

    private PreparedStatement getSelectCardsStatement()
    {
        Connection connection = connector.getConnection();
        try
        {
            select_cards_stmt = connection.prepareStatement(
                    SQL_SELECT_CARD,
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return select_cards_stmt;
    }

    private static final String SQL_SELECT_PLAYERS_ASC =
            "SELECT * FROM Player WHERE gameID = ? ORDER BY playerID ASC";

    private PreparedStatement select_players_asc_stmt = null;

    private PreparedStatement getSelectPlayersASCStatement() {
        if (select_players_asc_stmt == null) {
            Connection connection = connector.getConnection();
            try {
                // This statement does not need to be updatable
                select_players_asc_stmt = connection.prepareStatement(
                        SQL_SELECT_PLAYERS_ASC);
            } catch (SQLException e) {
                // TODO error handling
                e.printStackTrace();
            }
        }
        return select_players_asc_stmt;
    }

    private static final String SQL_SELECT_LASERS_ASC =
            "SELECT * FROM laser WHERE gameID = ? ORDER BY laserID ASC";

    private PreparedStatement select_lasers_asc_stmt = null;

    private PreparedStatement getSelectLasersASCStatement()
    {
        if (select_lasers_asc_stmt == null) {
            Connection connection = connector.getConnection();
            try {
                // This statement does not need to be updatable
                select_lasers_asc_stmt = connection.prepareStatement(
                        SQL_SELECT_LASERS_ASC);
            } catch (SQLException e) {
                // TODO error handling
                e.printStackTrace();
            }
        }
        return select_lasers_asc_stmt;
    }

    private static final String SQL_SELECT_WALLS_ASC =
            "SELECT * FROM wall WHERE gameID = ? ORDER BY wallID ASC";

    private PreparedStatement select_walls_asc_stmt = null;

    private PreparedStatement getSelectWallsASCStatement()
    {
        if (select_walls_asc_stmt == null) {
            Connection connection = connector.getConnection();
            try {
                // This statement does not need to be updatable
                select_walls_asc_stmt = connection.prepareStatement(
                        SQL_SELECT_WALLS_ASC);
            } catch (SQLException e) {
                // TODO error handling
                e.printStackTrace();
            }
        }
        return select_walls_asc_stmt;
    }

    private static final String SQL_SELECT_CHECKPOINTS_ASC =
            "SELECT * FROM checkpoint WHERE gameID = ? ORDER BY checkpointID ASC";

    private PreparedStatement select_checkpoints_asc_stmt = null;

    private PreparedStatement getSelectCheckpointASCStatement()
    {
        if (select_checkpoints_asc_stmt == null) {
            Connection connection = connector.getConnection();
            try {
                // This statement does not need to be updatable
                select_checkpoints_asc_stmt = connection.prepareStatement(
                        SQL_SELECT_CHECKPOINTS_ASC);
            } catch (SQLException e) {
                // TODO error handling
                e.printStackTrace();
            }
        }
        return select_checkpoints_asc_stmt;
    }

    private static final String SQL_SELECT_CARDS_ASC =
            "SELECT * FROM Register Where gameID = ? ORDER BY playerID ASC";

    private PreparedStatement select_cards_asc_stmt = null;

    private PreparedStatement getSelectCardASCStatement()
    {
        if(select_cards_asc_stmt == null)
        {
            Connection connection = connector.getConnection();
            try
            {
                select_cards_asc_stmt = connection.prepareStatement(
                        SQL_SELECT_CARDS_ASC);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return select_cards_asc_stmt;
    }

    private static final String SQL_SELECT_GAMES =
            "SELECT gameID, name FROM Game";

    private PreparedStatement select_games_stmt = null;

    private PreparedStatement getSelectGameIdsStatement() {
        if (select_games_stmt == null) {
            Connection connection = connector.getConnection();
            try {
                select_games_stmt = connection.prepareStatement(
                        SQL_SELECT_GAMES);
            } catch (SQLException e) {
                // TODO error handling
                e.printStackTrace();
            }
        }
        return select_games_stmt;
    }

    private boolean valueNullChecker(CommandCard card)
    {
        if(card == null) return true;
        else return false;
    }
}
