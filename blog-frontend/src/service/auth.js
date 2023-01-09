import { useHistory } from "react-router-dom";

export const sendAuthProcess = async (process, userInfo) => {

    try{
    const response = await fetch("/api/v1/user", {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userInfo)
    });

    return response.json();
}catch(e){
    console.log('errorMessage', e.message);
    return [];
}
}