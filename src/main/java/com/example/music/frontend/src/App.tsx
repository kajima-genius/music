
import logo from './logo.svg';
import './App.css';
import React from "react";

class App extends React.Component<{}, any> {
    constructor(props: any) {
        super(props);

        this.state = {
            users: [],
            isLoading: false
        };
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch('http://localhost:8080/users')
            .then(response => response.json())
            .then(data => this.setState({users: data, isLoading: false}));
    }

    render() {
        const {users, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        return (
            <div className="App">
                <div className="App-header">
                    <img src={logo} className="App-logo" alt="logo" />
                    <h2>Welcome to React</h2>
                </div>
                <div>
                    <h2>Users List</h2>
                    {users.map((user: any) =>
                        <div key={user.id}>
                            {user.id} {user.username} {user.email}
                        </div>
                    )}
                </div>
            </div>
        );
    }
}
export default App;


