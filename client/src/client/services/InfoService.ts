/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CharacterInfo } from '../models/CharacterInfo';
import { request as __request } from '../core/request';

export class InfoService {

    /**
     * Generate the characters details (background, class, race, starting items, etc.)
     * @returns CharacterInfo Characters info
     * @throws ApiError
     */
    public static async getInfo(): Promise<CharacterInfo> {
        const result = await __request({
            method: 'GET',
            path: `/info`,
        });
        return result.body;
    }

}