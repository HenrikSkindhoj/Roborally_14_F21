package dk.dtu.compute.se.pisd.roborally.model;

/**
 * ...
 *
 * @author Henrik Lynggard Skindhøj, s205464@student.dtu.dk
 * @version $Id: $Id
 */
public class ImpossibleMoveException extends Exception {

    private Player player;
    private Space space;
    private Heading heading;

    /**
     * <p>Constructor for ImpossibleMoveException.</p>
     *
     * @param player a {@link dk.dtu.compute.se.pisd.roborally.model.Player} object
     * @param space a {@link dk.dtu.compute.se.pisd.roborally.model.Space} object.
     * @param heading a {@link dk.dtu.compute.se.pisd.roborally.model.Heading} object.
     */
    public ImpossibleMoveException (Player player, Space space, Heading heading){
        super("Move impossible");
        this.player = player;
        this.space = space;
        this.heading = heading;
    }

}
