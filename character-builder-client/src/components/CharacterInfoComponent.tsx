import React from 'react';
import { SpriteComponent } from './SpriteComponent';
import { CharacterInfo, InfoService, ApiError, AbilityScore, Equipment } from '../client';
import './CharacterInfoComponent.css';

type State = {
    characterInfo?: CharacterInfo;
    error?: ApiError;
}

const getModifier = (abilityScore: AbilityScore): string => {
    const modifier = abilityScore.modifier;
    if (!modifier) {
        return '0';
    }
    if (modifier < 0) {
        return `${modifier}`;
    } else {
        return `+${modifier}`;
    }
}

const generateList = (listName?: string, values?: string[]): JSX.Element => {
    if (!values) {
        return (
            <div>
                <h3>{listName}</h3>
            </div>
        )
    }
    return (
        <div>
            <h3>{listName}</h3>
            <ul>
                {
                    values.map((value): JSX.Element => (
                        <li>{value}</li>
                    ))
                }
            </ul>
        </div>
    )
}

const getHp = (info: CharacterInfo): number => {
    if (!info.hitDice || !info.constitution || (info.constitution.modifier == null)) {
        return 0;
    }
    return info.hitDice + info.constitution.modifier;
}

const CharacterInfoComponent = () => {
    const [state, setData] = React.useState<State | undefined>(undefined);

    React.useEffect(() => {
        (async () => {
            try {
                const characterInfo: CharacterInfo = await InfoService.getInfo();
                setData({
                    characterInfo,
                });
            } catch (e) {
                setData({
                    error: e as ApiError,
                });
            }
        })();
    }, []);

    if (!state) {
        return (
            <div>
                Loading...
            </div>
        )
    }
    if (state.error) {
        return (
            <div>
                Error: {state.error.message}
            </div>
        )
    }
    if (!state.characterInfo) {
        return (
            <div>
                No character info
            </div>
        )
    }
    const characterInfo = state.characterInfo;
    const statNames: (keyof CharacterInfo)[] = [
        'intelligence',
        'wisdom',
        'strength',
        'constitution',
        'dexterity',
        'charisma',
    ];
    return (
        <div className="container">
            <div className="character-info">
                <h3>Name</h3>
                <p>{characterInfo.name}</p>
                <h3>Race</h3>
                <p>{characterInfo.race}</p>
                <h3>Class</h3>
                <p>{characterInfo.class}</p>
                <h3>Background</h3>
                <p>{characterInfo.background}</p>
                <div className='minor-stats'>
                    <div>
                        <h3>Alignment</h3>
                        <p>{characterInfo.alignment}</p>
                    </div>
                    <div>
                        <h3>Proficiency Modifier</h3>
                        <p>{`+${characterInfo.proficiencyModifier}`}</p>
                    </div>
                    <div>
                        <h3>Speed</h3>
                        <p>{characterInfo.speed}</p>
                    </div>
                    <div>
                        <h3>HP</h3>
                        <p>{getHp(characterInfo)}</p>
                    </div>
                    <div>
                        <h3>Hit Dice</h3>
                        <p>{`d${characterInfo.hitDice}`}</p>
                    </div>
                </div>
                <h3>Stats</h3>
                <div className='stats'>
                    <div />
                    {
                        statNames.map((statName): JSX.Element => (
                            <div>{statName.substring(0, 3).toUpperCase()}</div>
                        ))
                    }
                    <div>Base</div>
                    {
                        statNames.map((statName): JSX.Element => (
                            <div>{(characterInfo[statName] as AbilityScore)?.base}</div>
                        ))
                    }
                    <div>Modifier</div>
                    {
                        statNames.map((statName): JSX.Element => (
                            <div>{getModifier((characterInfo[statName] as AbilityScore))}</div>
                        ))
                    }
                    <div>Proficient</div>
                    {
                        statNames.map((statName): JSX.Element => (
                            <div>{(characterInfo[statName] as AbilityScore)?.proficient ? <span>&#10004;</span> : <span>&#10008;</span>}</div>
                        ))
                    }
                </div>
                <div className='lists'>
                    {
                        generateList('Skills', characterInfo.skills)
                    }
                    {
                        generateList('Proficiencies', characterInfo.proficiencies)
                    }
                    {
                        generateList('Languages', characterInfo.languages)
                    }
                    <div>
                        <h3>Equipment</h3>
                        <ul>
                            {
                                characterInfo.equipment?.map((equipment: Equipment): JSX.Element => (
                                    <li>{`${equipment.name}: ${equipment.quantity}`}</li>
                                ))
                            }
                        </ul>
                    </div>
                    <div className='long-list'>
                        {
                            generateList('Traits', characterInfo.traits)
                        }
                        {
                            generateList('Ideals', characterInfo.ideals)
                        }
                        {
                            generateList('Bonds', characterInfo.bonds)
                        }
                        {
                            generateList('Flaws', characterInfo.flaws)
                        }
                        {
                            generateList(characterInfo.feature?.name, characterInfo.feature?.description)
                        }
                    </div>
                </div>
            </div>
            <SpriteComponent characterInfo={characterInfo} />
        </div>
    )
}

export { CharacterInfoComponent };