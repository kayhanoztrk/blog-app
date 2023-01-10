import React,{useState, useEffect} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Author from '../Author/Author';
import { getAllUsers } from '../../service/user';

const useStyles = makeStyles((theme) => ({
    container: {
      display: "flex",
      flexWrap: "wrap",
      justifyContent: "center",
      alignItems: "center",
      backgroundColor: "#f0f5ff",
    },
  }));

const UserList = () => {

    const [userList, setUserList] = useState([]);
    const [isLoaded, setIsLoaded] = useState(false);
    const classes = useStyles();

    const getUserList = async() => {
        const userList = await getAllUsers();
        console.log('userList', userList);
        setUserList(userList);
        setIsLoaded(true);
    }

    useEffect(() => {
        getUserList();
    },[]);

    return(<div className={classes.container}>
        {
            userList.map((user) => (
                <Author user={user} />
            ))
        }
        </div>
        )
}

export default UserList;