import React, {useState} from 'react';
import './App.css';
import Header from './components/Header/Header';
import SideBar from './components/SideBar/SideBar';
import RecommendedVideos from './components/RecommendedVideos/RecommendedVideos';
import SearchPage from './components/SearchPage/SearchPage';
import VideoPlayer from './components/VideoPlayer/VideoPlayer';
import {BrowserRouter, BrowserRouter as Router, Route} from 'react-router-dom';
import {AuthContext} from "./context/AuthContext";
import Login from "./login/Login/Login";
import PrivateRoute from "./login/common/PrivateRoute";
import Signup from "./components/Signup/Signup";

function App() {

    const existingTokens = JSON.parse(localStorage.getItem("user"));
    const [authTokens, setAuthTokens] = useState(existingTokens);

    const setTokens = (data) => {
        localStorage.setItem("user", JSON.stringify(data));
        setAuthTokens(data);
    }

    return (
        <AuthContext.Provider
            value={{authTokens, setAuthTokens: setTokens}}>
            <BrowserRouter>
                <Route setAuthTokens={setAuthTokens} path="/login" component={Login} exact={true}/>
                <Route path="/signup" component={Signup} />
                <PrivateRoute path='/home' component={RecommendedVideosPage} />
                <PrivateRoute path='/video/:videoId' component={VideoPlayerPage} />
                <PrivateRoute path='/search/:searchQuery' component={SearchVideoPage} />
            </BrowserRouter>
        </AuthContext.Provider>
    );
}

function RecommendedVideosPage() {
    return (
        <div>
            <Header/>
            <div className="app__mainpage">
                <SideBar/>
                <RecommendedVideos/>
            </div>
        </div>
    );
}

function VideoPlayerPage() {
    return (
        <div><Header/>
            <div className="app__mainpage">
                <SideBar/>
                <VideoPlayer/>
            </div>
        </div>
    );
}

function SearchVideoPage() {
    return (
        <div><Header/>
            <div className="app__mainpage">
                <SideBar/>
                <SearchPage/>
            </div>
        </div>
    );
}


export default App;
