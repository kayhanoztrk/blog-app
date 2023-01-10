import React,{useState, useEffect} from "react";
import Alert from '@mui/material/Alert';

const NotFound = (props) => {

    const {message} = props
    return(
        <div>
            <Alert severity="error"> {message}</Alert>
        </div>
    )
}
export default NotFound;