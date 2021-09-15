import React, {Component} from 'react';
import './StartPage.css';
import StartHeader from "./Header/StartHeader";
import {isLogin} from "../../login/common/IsLogin";
import {Redirect} from "react-router";

class StartPage extends Component {
    render() {
        return (
            !isLogin() ? (
                    <div className="home-container">
                        <StartHeader/>
                        <div className="container">
                            <div className="graf-bg-container">
                                <div className="graf-layout">
                                    <div className="graf-circle"></div>
                                    <div className="graf-circle"></div>
                                    <div className="graf-circle"></div>
                                    <div className="graf-circle"></div>
                                    <div className="graf-circle"></div>
                                    <div className="graf-circle"></div>
                                    <div className="graf-circle"></div>
                                    <div className="graf-circle"></div>
                                    <div className="graf-circle"></div>
                                    <div className="graf-circle"></div>
                                    <div className="graf-circle"></div>
                                </div>
                            </div>
                            <h1 className="home-title">Youtube-clone app</h1>
                        </div>
                    </div>
                ) :
                <Redirect
                    to={{
                        pathname: '/home',
                        state: {from: this.props.location}
                    }}
                />
        )
    }
}

export default StartPage;