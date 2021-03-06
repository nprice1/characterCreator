/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CharacterInfo } from '../models/CharacterInfo';
import { request as __request } from '../core/request';

export class SpriteService {

    /**
     * Generate the characters sprite sheet
     * @param requestBody
     * @returns any The sprite sheet for the provided character info
     * @throws ApiError
     */
    public static async getSpriteSheet(
        requestBody?: CharacterInfo,
    ): Promise<any> {
        const result = await __request({
            method: 'POST',
            path: `/sprite`,
            body: requestBody,
            headers: {
                'Accept': 'image/png'
            },
        });
        return result.body;
    }

    /**
     * Get an existing sprite sheet by name
     * @param name The name of the sprite sheet to fetch
     * @returns any The sprite sheet for the provided character info
     * @throws ApiError
     */
    public static async getSpriteSheetByName(
        name: string,
    ): Promise<any> {
        const result = await __request({
            method: 'GET',
            path: `/sprite/${name}`,
            headers: {
                'Accept': 'image/png'
            },
        });
        return result.body;
    }

}