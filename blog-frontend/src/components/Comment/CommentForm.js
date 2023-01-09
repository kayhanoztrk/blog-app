
import React, {useState, useEffect} from "react";
import { CardContent } from "@mui/material";
import { OutlinedInput } from "@mui/material";
import { InputAdornment } from "@mui/material";
import { Link } from "react-router-dom";
import { Button } from "@mui/material";
const CommentForm = () => {


    const handleChange = (value) => {
        console.log('value', value);
    }

    const handleSubmit = () => {
        console.log('handled submit!');
    }

    return(
        <CardContent>

        <OutlinedInput
        id="outlined-adornment-amount"
        multiline
        inputProps = {{maxLength : 25}}
        fullWidth
        onChange={(e) => handleChange(e.target.value)}
        startAdornment = {
            <InputAdornment position="start">
                <Link>
                  deneme
                </Link>
            </InputAdornment>
        }
        endAdornment = {
            <InputAdornment position = "end">
                  <Button variant="contained"
         style= {{background: 'linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)',
         color: 'white'}}
         onClick = {handleSubmit}  
        >Comment</Button>
            </InputAdornment>
        }
        value = "value"
        style = {{ color : "black",backgroundColor: 'white'}}
        ></OutlinedInput>
        </CardContent>
    )
}

export default CommentForm;