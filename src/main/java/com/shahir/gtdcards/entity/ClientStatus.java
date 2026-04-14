package com.shahir.gtdcards.entity;

/**
 * Enumeration representing the lifecycle status of a client.
 *
 * <p>
 * In a banking and GTD Cards context, client status is used instead of hard
 * deletion to preserve audit trails and historical records.
 * </p>
 *
 * <ul>
 * <li>{@link #ACTIVE} – Client is active and allowed to perform
 * transactions</li>
 * <li>{@link #INACTIVE} – Client is temporarily inactive</li>
 * <li>{@link #SUSPENDED} – Client is suspended due to compliance or risk
 * reasons</li>
 * </ul>
 */
public enum ClientStatus {

	/**
	 * Client is active and fully operational.
	 */
	ACTIVE,

	/**
	 * Client is inactive but not blocked permanently.
	 */
	INACTIVE,

	/**
	 * Client is suspended due to regulatory, risk, or operational reasons.
	 */
	SUSPENDED
}
