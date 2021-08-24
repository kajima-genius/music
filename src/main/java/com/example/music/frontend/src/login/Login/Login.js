import React, {Component} from "react";
import {Link, Redirect} from "react-router-dom";
import axios from "axios";
import SimpleReactValidator from "simple-react-validator";
import googleLogo from "../img/google-logo.png"
import "./Login.css"

export default class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoggedIn: false,
            email: "",
            password: ""
        }
        this.validator = new SimpleReactValidator({
            validators: {
                email: {
                    message: 'Email is not correct or maximum number of characters is more than 100',
                    rule: (val, params, validator) => {
                        return validator.helpers.testRegex(val, /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/) && val.length < 100;
                    },
                    messageReplace: (message, params) => message.replace(':values', this.helpers.toSentence(params)),  // optional
                    required: false  // optional
                },
            }
        });
    }

    postLogin = (event) => {
        event.preventDefault();
        this.validator.hideMessages();
        if (!this.validator.allValid()) {

            this.validator.showMessages();
            this.forceUpdate();
            return;
        }

        axios.post("http://localhost:8080/auth/login", {
            "email": this.state.email,
            "password": this.state.password
        }).then(result => {
            localStorage.setItem("user", JSON.stringify(result.data));
            this.setState({isLoggedIn: true});
        }).catch(e => {
            console.log(e.message)
        });
    }

    render() {
        return (
            <div className="login-container">
                {this.state.isLoggedIn && <Redirect to="/home"/>}
                <div className="login-content">
                    <h1 className="login-title">Login to SpringSocial</h1>
                    <div className="social-login">
                        <a className="btn btn-block social-btn google">
                            <img src={googleLogo} alt="Google"/> Log in with Google</a>
                    </div>
                    <div className="or-separator">
                        <span className="or-text">OR</span>
                    </div>
                    <form>
                        <div className="form-item">
                            <input type="email" name="email"
                                   className="form-control" placeholder="Email"
                                   value={this.state.email}
                                   onChange={e => {
                                       this.setState({email: e.target.value});
                                   }}
                                   placeholder="Email"/>
                        </div>
                        <div className="form-item">
                            <input type="password" name="password"
                                   className="form-control" placeholder="Password"
                                   value={this.state.password}
                                   onChange={e => {
                                       this.setState({password: e.target.value});
                                   }}
                                   placeholder="password"
                            />
                        </div>
                        <div className="form-item">
                            <button type="button" className="btn btn-block btn-primary" onClick={this.postLogin}>Login
                            </button>
                        </div>
                    </form>
                    <span className="signup-link">New user? <Link to="/signup">Sign up!</Link></span>
                </div>
            </div>
        );
    }
}