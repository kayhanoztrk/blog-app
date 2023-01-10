export const getUserById = async (userId) => {
    try{
    const response = await fetch("/api/v1/user/" + userId);
    return response.json();
}catch(e){
    console.log('e.message', e.message);
    return [];
}
}


export const getAllUsers = async () => {
    try{
        const response = await fetch("/api/v1/user");
        return response.json();
    }catch(e){
        console.log('e.message', e.message);
        return [];
    }

}