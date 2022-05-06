import React from 'react';
import './App.css';
import { CharacterInfoComponent } from './characterInfo/CharacterInfoComponent';

type State = {
  useApollo: boolean;
}

const App = () => {
  const [state, setData] = React.useState<State>({
      useApollo: true,
  });

  return (
    <div className="App">
      <div className="client-selector">
        <button onClick={() => setData({useApollo: true})}>Use Apollo</button>
        <button onClick={() => setData({useApollo: false})}>Use REST</button>
      </div>
      <CharacterInfoComponent useApollo={state.useApollo} />
    </div>
  );
}

export default App;
