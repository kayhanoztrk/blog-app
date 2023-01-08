import React, {useState, useEffect} from "react";
import { makeStyles } from '@material-ui/core/styles';
import RecipeReviewCard from "../Card/Card";

const useStyles = makeStyles((theme) => ({
    container:{
        display :"flex",
        flexWrap :"wrap",
        justifyContent : "center",
        alignItems: "center",
        backgroundColor: "#f0f5ff"

    }
}));

const Home = () => {
    const [isLoaded, setIsLoaded] = useState(false);
    const [postList, setPostList] = useState([]);



    const classes = useStyles();
    return(
        <div className={classes.container}>
            <RecipeReviewCard />
            <RecipeReviewCard />
            <RecipeReviewCard />

        </div>

    )
}

export default Home;