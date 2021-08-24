import React, {Component} from 'react';
import './Signup.css';
import {Link, Redirect} from 'react-router-dom'
import Alert from 'react-s-alert';
import {isLogin} from "../../login/common/IsLogin";
import axios from "axios";

class Signup extends Component {
    render() {
        if (isLogin()) {
            return <Redirect
                to={{
                    pathname: "/home",
                    state: {from: this.props.location}
                }}/>;
        }

        return (
            <div className="signup-container">
                <div className="signup-content">
                    {/*<h1 className="signup-title">Signup with SpringSocial</h1>*/}
                    {/*<div className="or-separator">*/}
                    {/*    <span className="or-text">OR</span>*/}
                    {/*</div>*/}
                    <SignupForm {...this.props} />
                    <span className="login-link">Already have an account? <Link to="/login">Login!</Link></span>
                </div>
            </div>
        );
    }
}

class SignupForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            dateOfBirth: '',
            gender: '',
            email: '',
            password: ''
        }

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleInputChange(event) {
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value;

        this.setState({
            [inputName]: inputValue
        });
    }

    handleSubmit(event) {
        event.preventDefault();
        axios.post("http://localhost:8080/users/registration", {
            username: this.state.username,
            dateOfBirth: this.state.dateOfBirth,
            gender: this.state.gender,
            email: this.state.email,
            password: this.state.password
        })
            .then(response => {
                Alert.success("You're successfully registered. Please login to continue!");
                this.props.history.push("/login");
            }).catch(error => {
            Alert.error((error && error.message) || 'Oops! Something went wrong. Please try again!');
        });
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <div className="form-item">
                    <input type="text" name="username"
                           className="form-control" placeholder="Username"
                           value={this.state.username} onChange={this.handleInputChange} required/>
                </div>
                <div className="form-item">
                    <input type="text" name="dateOfBirth"
                           className="form-control" placeholder="Birthday"
                           value={this.state.dateOfBirth} onChange={this.handleInputChange} required/>
                </div>
                <div className="form-item">
                    <div className="rs-select2 js-select-simple select--no-search form-control">
                        <select name="gender">
                            <option value="none" disabled="disabled" selected="selected" hidden="hidden">Select an
                                Gender
                            </option>
                            <option value={this.state.gender = "MALE"} onChange={this.handleInputChange}>Male</option>
                            <option value={this.state.gender = "FEMALE"} onChange={this.handleInputChange}>Female</option>
                        </select>
                        <div className="select-dropdown"></div>
                    </div>
                </div>
                <div className="form-item">
                    <input type="email" name="email"
                           className="form-control" placeholder="Email"
                           value={this.state.email} onChange={this.handleInputChange} required/>
                </div>
                <div className="form-item">
                    <input type="password" name="password"
                           className="form-control" placeholder="Password"
                           value={this.state.password} onChange={this.handleInputChange} required/>
                </div>
                <div className="form-item">
                    <button type="submit" className="btn btn-block btn-primary">Sign Up</button>
                </div>
            </form>

        );
    }
}

export default Signup