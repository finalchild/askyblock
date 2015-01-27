package com.wasteofplastic.askyblock;

import java.util.Date;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * Stores all the info about an island
 * Managed by GridManager
 * @author tastybento
 *
 */
public class Island {

    // Coordinates of the island area
    private int minX;
    private int minZ;
    // Protection size
    private int protectionRange;
    // Height of island
    private int y;
    // The actual center of the island itself
    private Location center;
    // World the island is in
    private World world;
    // The owner of the island
    private UUID owner;
    // Time parameters
    private Date createdDate;
    private Date updatedDate;
    // A password associated with the island
    private String password;
    // Votes for how awesome the island is
    private int votes;
    private int islandDistance;

    public Island(String serial) {
	// Deserialize
	// Format:
	// x:height:z:protection range:island distance:owner UUID
	String[] split = serial.split(":");
	try {
	    protectionRange = Integer.parseInt(split[3]);
	    minX = Integer.parseInt(split[0]) - protectionRange/2;
	    y = Integer.parseInt(split[1]);
	    minZ = Integer.parseInt(split[2]) - protectionRange/2;
	    islandDistance = Integer.parseInt(split[4]);
	    if (!split[5].equals("null")) {
		owner = UUID.fromString(split[5]);
	    }
	    this.world = ASkyBlock.getIslandWorld();
	    this.center = new Location(world,minX,y,minZ);
	    this.createdDate = new Date();
	    this.updatedDate = createdDate;
	    this.password = "";
	    this.votes = 0;
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * Add a new island using the island center method
     * @param minX
     * @param minZ
     */
    public Island(int x, int z) {
	// Calculate min minX and z
	this.minX = x - Settings.island_protectionRange/2;
	this.minZ = z - Settings.island_protectionRange/2;
	this.y = Settings.island_level;
	this.islandDistance = Settings.islandDistance;
	this.protectionRange = Settings.island_protectionRange;
	this.world = ASkyBlock.getIslandWorld();
	this.center = new Location(world,x,y,z);
	this.createdDate = new Date();
	this.updatedDate = createdDate;
	this.password = "";
	this.votes = 0;
    }

    public Island(int x, int z, UUID owner) {
	// Calculate min minX and z
	this.minX = x - Settings.island_protectionRange/2;
	this.minZ = z - Settings.island_protectionRange/2;
	this.y = Settings.island_level;
	this.islandDistance = Settings.islandDistance;
	this.protectionRange = Settings.island_protectionRange;
	this.world = ASkyBlock.getIslandWorld();
	this.center = new Location(world,x,y,z);
	this.createdDate = new Date();
	this.updatedDate = createdDate;
	this.password = "";
	this.votes = 0;
	this.owner = owner;
    }
    /**
     * @param minX
     * @param z
     * @param protectionRange
     * @param center
     * @param owner
     * @param createdDate
     * @param updatedDate
     * @param password
     * @param votes
     */
    public Island(int x, int z, int protectionRange, Location center, UUID owner, Date createdDate, Date updatedDate, String password, int votes) {
	this.minX = x;
	this.minZ = z;
	this.protectionRange = protectionRange;
	this.center = center;
	this.world = center.getWorld();
	this.y = center.getBlockY();
	this.owner = owner;
	this.createdDate = createdDate;
	this.updatedDate = updatedDate;
	this.password = password;
	this.votes = votes;
    }

    /**
     * Checks if a location is within this island's protected area
     * @param loc
     * @return
     */
    public boolean onIsland(Location target) {
	if (target.getWorld().equals(world)) {
	    if (target.getX() > center.getBlockX() - protectionRange / 2
		    && target.getX() < center.getBlockX() + protectionRange / 2
		    && target.getZ() > center.getBlockZ() - protectionRange / 2
		    && target.getZ() < center.getBlockZ() + protectionRange / 2) {
		return true;
	    }
	}
	return false;	
    }

    /**
     * @return the minX
     */
    public int getMinX() {
	return minX;
    }
    /**
     * @param minX the minX to set
     */
    public void setMinX(int minX) {
	this.minX = minX;
    }
    /**
     * @return the z
     */
    public int getMinZ() {
	return minZ;
    }
    /**
     * @param z the z to set
     */
    public void setMinZ(int minZ) {
	this.minZ = minZ;
    }
    /**
     * @return the protectionRange
     */
    public int getProtectionSize() {
	return protectionRange;
    }
    /**
     * @param protectionRange the protectionRange to set
     */
    public void setProtectionSize(int protectionSize) {
	this.protectionRange = protectionSize;
    }
    /**
     * @return the center
     */
    public Location getCenter() {
	return center;
    }
    /**
     * @param center the center to set
     */
    public void setCenter(Location center) {
	this.center = center;
    }
    /**
     * @return the owner
     */
    public UUID getOwner() {
	return owner;
    }
    /**
     * @param owner the owner to set
     */
    public void setOwner(UUID owner) {
	this.owner = owner;
    }
    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
	return createdDate;
    }
    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
    }
    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
	return updatedDate;
    }
    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(Date updatedDate) {
	this.updatedDate = updatedDate;
    }
    /**
     * @return the password
     */
    public String getPassword() {
	return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
	this.password = password;
    }
    /**
     * @return the votes
     */
    public int getVotes() {
	return votes;
    }
    /**
     * @param votes the votes to set
     */
    public void setVotes(int votes) {
	this.votes = votes;
    }

    protected String serialize() {
	// x:height:z:protection range:island distance:owner UUID
	String ownerString = "null";
	if (owner != null) {
	    ownerString = owner.toString();
	}
	return center.getBlockX() + ":" + center.getBlockY() + ":" + center.getBlockZ() + ":" + protectionRange 
		+ ":" + islandDistance + ":" + ownerString;
    }

}