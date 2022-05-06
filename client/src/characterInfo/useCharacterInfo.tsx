import { gql } from '@apollo/client';
import React from 'react';
import { CharacterInfo, InfoService } from '../client';
import {
    ApolloClient,
    InMemoryCache,
} from "@apollo/client";
  
const apolloClient = new ApolloClient({
    uri: 'http://localhost:8080/graphql',
    cache: new InMemoryCache()
});

type State = {
    loading: boolean,
    characterInfo?: CharacterInfo;
    error?: Error;
};

export type HookResponse = {
    loading: boolean;
    error?: Error;
    characterInfo?: CharacterInfo;
};

export const useCharacterInfo = (useApollo: boolean): HookResponse => {
    const [state, setData] = React.useState<State>({
        loading: true,
    });

    React.useEffect(() => {
        (async () => {
            try {
                let characterInfo: CharacterInfo;
                if (useApollo) {
                    characterInfo = await getInfoApollo();
                } else {
                    characterInfo = await getInfoRest();
                }
                setData({
                    loading: false,
                    characterInfo,
                });
            } catch (e) {
                setData({
                    loading: false,
                    error: e as Error,
                });
            }
        })();
    }, [useApollo]);

    return {
        loading: state.loading,
        error: state.error,
        characterInfo: state.characterInfo,
    };
};

const QUERY = gql`
    query {
        newCharacter {
            name
            race
            characterClass
            background
            alignment
            speed
            hitDice
            skills
            proficiencies
            proficiencyModifier
            strength {
                base
                modifier
                proficient
            }
            dexterity {
                base
                modifier
                proficient
            }
            intelligence {
                base
                modifier
                proficient
            }
            wisdom {
                base
                modifier
                proficient
            }
            constitution {
                base
                modifier
                proficient
            }
            charisma {
                base
                modifier
                proficient
            }
            languages
            feature {
                name
                description
            }
            ideals
            traits
            flaws
            bonds
            equipment {
                name
                quantity
            }
            spriteSheet
        }
    }
`; 

type NewCharacterResponse = {
    newCharacter: CharacterInfo;
}

const getInfoApollo = async (): Promise<CharacterInfo> => {
    return (await apolloClient.query<NewCharacterResponse>({ query: QUERY, fetchPolicy: 'no-cache'})).data.newCharacter;
};

const getInfoRest = async (): Promise<CharacterInfo> => {
    return await InfoService.getInfo();
};
