import React from 'react';
import Spritesheet from 'react-responsive-spritesheet';
import { SpriteService, ApiError, CharacterInfo } from '../client';
import './SpriteComponent.css';

type Animation = {
    id: number,
    availableRows: number[];
    frames: number;
    currentRowIndex: number;
    loop: boolean;
}

const walk: Animation = {
    id: 0,
    availableRows: [10, 11, 8, 9],
    frames: 7,
    currentRowIndex: 0,
    loop: true,
}

const spell: Animation = {
    id: 1,
    availableRows: [2, 3, 0, 1],
    frames: 6,
    currentRowIndex: 0,
    loop: true,
}

const thrust: Animation = {
    id: 2,
    availableRows: [6, 7, 4, 5],
    frames: 6,
    currentRowIndex: 0,
    loop: true,
}

const slash: Animation = {
    id: 3,
    availableRows: [14, 15, 12, 13],
    frames: 5,
    currentRowIndex: 0,
    loop: true,
}

const shoot: Animation = {
    id: 4,
    availableRows: [18, 19, 16, 17],
    frames: 12,
    currentRowIndex: 0,
    loop: true,
}

const die: Animation = {
    id: 5,
    availableRows: [20],
    frames: 5,
    currentRowIndex: 0,
    loop: false,
}

const numColumns: number = 13;

type State = {
    sprite?: any;
    error?: ApiError;
    animation?: Animation;
}

type Props = {
    characterInfo: CharacterInfo | undefined;
}

const getStartAt = (animation?: Animation): number => {
    if (!animation) {
        return 0;
    }
    return (numColumns * animation.availableRows[animation.currentRowIndex]) + 1;
}

const getEndAt = (animation?: Animation): number => {
    if (!animation) {
        return 0;
    }
    return getStartAt(animation) + animation.frames;
}

const SpriteComponent = ({ characterInfo }: Props) => {
    const [state, setState] = React.useState<State | undefined>(undefined);

    React.useEffect(() => {
        (async () => {
            try {
                const sprite: any = await SpriteService.getSpriteSheet(characterInfo);
                setState({
                    sprite,
                    animation: walk,
                });
            } catch (e) {
                setState({
                    error: e,
                });
            }
        })();
    }, [characterInfo]);

    const rotate = (offset: number) => {
        const currentAnimation: Animation | undefined = state?.animation;
        if (!currentAnimation) {
            return;
        }
        const numRows = currentAnimation.availableRows.length;
        let newIndex = currentAnimation.currentRowIndex + offset;
        if (newIndex < 0) {
            newIndex = numRows - 1;
        } else if (newIndex >= numRows) {
            newIndex = 0;
        }
        setState({
            ...state,
            animation: {
                ...currentAnimation,
                currentRowIndex: newIndex,
            }
        })
    }

    const setAnimation = (animation: Animation) => {
        if (!state) {
            return;
        }
        setState({
            ...state,
            animation: animation,
        });
    }

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
    if (!state.animation) {
        return (
            <div>
                No animation defined
            </div>
        )
    }

    return (
        <div className="sprite-container">
            <div className="sprite">
                <Spritesheet
                    key={state.animation.id + state.animation.currentRowIndex}
                    image={URL.createObjectURL(state.sprite)}
                    widthFrame={64}
                    heightFrame={64}
                    fps={12}
                    loop={state.animation.loop}
                    startAt={getStartAt(state.animation)}
                    endAt={getEndAt(state.animation)}
                />
            </div>
            <button onClick={() => rotate(-1)}>{'<<'}</button>
            <button onClick={() => setAnimation(walk)}>Walk</button>
            <button onClick={() => setAnimation(spell)}>Spell</button>
            <button onClick={() => setAnimation(thrust)}>Thrust</button>
            <button onClick={() => setAnimation(slash)}>Slash</button>
            <button onClick={() => setAnimation(shoot)}>Shoot</button>
            <button onClick={() => setAnimation(die)}>Die</button>
            <button onClick={() => rotate(1)}>{'>>'}</button>
        </div>
    )
}

export { SpriteComponent };