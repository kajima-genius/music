import React, {useState, useEffect} from 'react';
import "./SearchPage.css";
import TuneIcon from '@material-ui/icons/Tune';
import ChannelRow from './../ChannelRow/ChannelRow';
import VideoRow from './../VideoRow/VideoRow';
import {useParams} from 'react-router';
import axios from 'axios';
import {DateTime} from 'luxon';
import {Link} from 'react-router-dom';
import CircularProgress from '@material-ui/core/CircularProgress';
import Alert from '@material-ui/lab/Alert';
import authHeader from "../../context/AuthHeader";

const SearchPage = (props) => {
    let {searchQuery} = useParams();

    const [channelRow, setChannelRow] = useState('');
    const [videoRows, setVideoRows] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [isError, setIsError] = useState(false);

    useEffect(() => {
        setChannelRow('');
        setVideoRows([]);
        axios
            .get(`https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=1&type=channel&q=${searchQuery}&safeSearch=none&key=AIzaSyCl3eoqeF4CxLHaZHG01LfmMoZVfqCoozY`)
            .then(response => {
                createChannelRow(response.data['items'][0]);
            })


        axios
            .get(`http://localhost:8080/videos/search?queryTerm=${searchQuery}&maxResults=50`, {headers: authHeader()})
            .then(response => {
                createVideoRows(response.data);
                setIsError(false);
            })
            .catch(error => {
                console.log(error);
                setIsError(true);
                setIsLoading(false);
            })

    }, [searchQuery])


    async function createChannelRow(channel) {
        const channelId = channel.id.channelId;
        const response = await axios
            .get(`https://www.googleapis.com/youtube/v3/channels?part=statistics&id=${channelId}&key=AIzaSyCl3eoqeF4CxLHaZHG01LfmMoZVfqCoozY`)
        const noOfVideos = response.data.items[0].statistics.videoCount;
        const subs = response.data.items[0].statistics.subscriberCount;
        const snippet = channel.snippet;
        const title = snippet.title;
        const description = snippet.description;
        const image = snippet.thumbnails.medium.url;
        setChannelRow({
            channelId,
            image,
            title,
            subs,
            noOfVideos,
            description
        });
    }

    async function createVideoRows(videos) {
        let newVideoRows = [];
        for (const video of videos) {
            const videoId = video.youtubeId;
            const views = video.viewCount;
            const description = video.description
            const title = video.title;
            const image = video.imageUrl;
            const timestamp = DateTime.fromMillis(video.timestamp.value).toRelative();
            const channel = video.channelTitle;

            newVideoRows.push({
                videoId,
                title,
                image,
                views,
                timestamp,
                channel,
                description
            });
        }
        ;
        setVideoRows(newVideoRows);
        setIsLoading(false);
    }

    if (isError) {
        return <Alert severity="error" className='loading'>No Results found!</Alert>
    }
    return (
        <div className="searchpage">
            <div className="searchpage__filter">
                <TuneIcon/>
                <h2>Filter</h2>
            </div>
            {isLoading ? <CircularProgress className='loading' color='secondary'/> : null}
            <hr/>
            {!isLoading ? <ChannelRow
                key={channelRow.channelId}
                image={channelRow.image}
                channel={channelRow.title}
                subs={channelRow.subs}
                noOfVideos={channelRow.noOfVideos}
                description={channelRow.description}
            /> : null
            }
            <hr/>
            {
                videoRows.map(item => {
                    return (
                        <Link key={item.videoId} to={`/video/${item.videoId}`}>
                            <VideoRow
                                title={item.title}
                                image={item.image}
                                views={item.views}
                                timestamp={item.timestamp}
                                channel={item.channel}
                                description={item.description}
                            />
                        </Link>
                    )
                })

            }

        </div>
    )
}

export default SearchPage;


