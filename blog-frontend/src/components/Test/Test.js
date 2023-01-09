import React, {useState, useEffect} from 'react';


const Test = (props) => {

    const [data, setData] = useState([]);

    console.log('propsTest', props);
    const {id, username} = props;

    console.log('propsTest', props);

    useEffect(() => {
        setData([{name: 'Kayhan'}]);
        console.log('test component effect etkisi!');
    },[]);



    return(
        <div>
             {console.log('test component is loading')}
            <strong>Test Component!
                {username != null ? "naber username dolu" + username : "there is no record!!"}
            </strong>
        </div>
    )
}

export default Test;