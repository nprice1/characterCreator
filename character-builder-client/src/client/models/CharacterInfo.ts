/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { AbilityScore } from './AbilityScore';
import type { Equipment } from './Equipment';
import type { Feature } from './Feature';

/**
 * The general details of a character
 */
export type CharacterInfo = {
    /**
     * The name of the character
     */
    name?: string;
    /**
     * The race of the character
     */
    race?: string;
    /**
     * The class for the character
     */
    class?: string;
    /**
     * The name of the background for the character
     */
    background?: string;
    /**
     * The alignment for the character
     */
    alignment?: string;
    strength?: AbilityScore;
    dexterity?: AbilityScore;
    intelligence?: AbilityScore;
    wisdom?: AbilityScore;
    constitution?: AbilityScore;
    charisma?: AbilityScore;
    /**
     * The proficiency modifier for the character
     */
    proficiencyModifier?: number;
    skills?: Array<string>;
    proficiencies?: Array<string>;
    languages?: Array<string>;
    equipment?: Array<Equipment>;
    /**
     * The movement speed for the character
     */
    speed?: string;
    /**
     * The hit dice for the character
     */
    hitDice?: number;
    feature?: Feature;
    traits?: Array<string>;
    ideals?: Array<string>;
    bonds?: Array<string>;
    flaws?: Array<string>;
}
