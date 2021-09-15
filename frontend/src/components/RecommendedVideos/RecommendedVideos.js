import React, {useEffect, useState} from 'react';
import VideoCard from './../VideoCard/VideoCard';
import './RecommendedVideos.css';
import axios from 'axios';
import {DateTime} from 'luxon';
import CircularProgress from '@material-ui/core/CircularProgress';
import Alert from '@material-ui/lab/Alert';
import {Link} from "react-router-dom";
import authHeader from "../../context/AuthHeader";


const RecommendedVideos = () => {

    const [videoCards, setVideoCards] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [isError, setIsError] = useState(false);

    useEffect(() => {
        console.log(authHeader());
        axios
            .get(`http://localhost:8080/videos/trends`,{headers : authHeader()})
            .then(response => {
                console.log(response.data)
                createVideoCards(response.data);
            })
            .catch(error => {
                console.log(error);
                setIsError(true);
            })
    }, [])

    async function createVideoCards(videoItems) {
        let newVideoCards = [];
        for (const video of videoItems) {
            const videoId = video.youtubeId;
            const channelId = video.channelId;
            const response = await axios
                .get(`https://www.googleapis.com/youtube/v3/channels?part=snippet&id=${channelId}&key=AIzaSyC5k0VruOYaTht90TTecW237lLLgOQ7s4g`)
            const channelImage = response.data.items[0].snippet.thumbnails.medium.url;

            const title = video.title;
            const image = video.imageUrl;
            const views = video.viewCount;
            const timestamp = DateTime.fromISO(video.timestamp.value).toRelative();
            const channel = video.channelTitle;

            newVideoCards.push({
                videoId,
                image,
                title,
                channel,
                views,
                timestamp,
                channelImage
            });
        }
        ;
        setVideoCards(newVideoCards);
        setIsLoading(false);
    }

    if (isError) {
        return <Alert severity="error" className='loading'>No Results found!</Alert>
    }
    return (

        <div className='recommendedvideos'>
            {isLoading ? <CircularProgress className='loading' color='secondary'/> : null}
            <div className="recommendedvideos__videos">
                {
                    videoCards.map(item => {
                        return (
                            <Link key={item.videoId} to={`/video/${item.videoId}`}>
                                <VideoCard
                                    title={item.title}
                                    image={item.image}
                                    views={item.views}
                                    timestamp={item.timestamp}
                                    channel={item.channel}
                                    channelImage={item.channelImage}
                                />
                            </Link>
                        )
                    })
                }
            </div>
        </div>
    )
}

export default RecommendedVideos;