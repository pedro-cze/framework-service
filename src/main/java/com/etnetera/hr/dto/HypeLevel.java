package com.etnetera.hr.dto;

/**
 * The HypeLevel enum represents basic technology hype levels.
 * According to gartner @link https://www.gartner.com/en/research/methodologies/gartner-hype-cycle
 * there are 5 such levels:
 *
 * - ON_THE_RISE:               A period of rapid development and growing interest
 * - AT_THE_PEAK:               The technology seems to be the subject of every computing-related blog or tweet
 * - SLIDING_INTO_THE_TROUGH:   Less-favorable stories start to emerge as most companies realize things aren't as easy
 *                              as they first seemed
 * - CLIMBING_THE_SLOPE:        The technology matures on the basis of early feedback, and obstacles in performance,
 *                              integration, user adoption and business case justification are overcome.
 * - ENTERING_THE_PLATEAU:      Beginning of mainstream adoption, when the real-world
 *                              benefits of the technology are predictable and broadly acknowledged
 */
public enum HypeLevel {

    ON_THE_RISE,
    AT_THE_PEAK,
    SLIDING_INTO_THE_TROUGH,
    CLIMBING_THE_SLOPE,
    ENTERING_THE_PLATEAU
}
