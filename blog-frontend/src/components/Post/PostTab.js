import React, { useState, useEffect } from "react";
import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import { makeStyles } from "@material-ui/core/styles";
import PostList from "./PostList";
import PostCard from "./PostCard";
import { getPostPublishedList, getPostDraftList } from "../../service/post";

interface TabPanelProps {
  children?: React.ReactNode;
  index: number;
  value: number;
}

function TabPanel(props: TabPanelProps) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box sx={{ p: 3 }}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

function a11yProps(index: number) {
  return {
    id: `simple-tab-${index}`,
    "aria-controls": `simple-tabpanel-${index}`,
  };
}

const useStyles = makeStyles((theme) => ({
  container: {
    display: "flex",
    minWidth: 600,
    margin: "auto",
    width: "100%",
    flexDirection: "row",
    flexWrap: "wrap",
    justifyContent: "flex-end",
    alignItems: "center",
    backgroundColor: "#f0f5ff",
  },
}));

const PostTab = () => {
  const [value, setValue] = React.useState(0);
  const [postPublishedList, setPostPublishedList] = useState([]);
  const [postDraftList, setPostDraftList] = useState([]);
  const [isLoaded, setIsLoaded] = useState(false);

  const findPostPublishedList = async () => {
    const postList = await getPostPublishedList();
    console.log("postList", postList);
    setPostPublishedList(postList);
    setIsLoaded(true);
  };
  const findPostDraftList = async () => {
    const postList = await getPostDraftList();
    console.log("postDraftList", postList);
    setPostDraftList(postList);
    setIsLoaded(true);
  };

  useEffect(() => {
    findPostPublishedList();
    findPostDraftList();
    console.log("postPublishedList", postPublishedList);
  }, []);

  const classes = useStyles();

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue);
  };

  return (
    <div className={classes.container}>
      <Box sx={{ width: "100%" }}>
        <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
          <Tabs
            value={value}
            onChange={handleChange}
            aria-label="basic tabs example"
          >
            <Tab label="Drafts" {...a11yProps(0)} />
            <Tab label="Published Contents" {...a11yProps(1)} />
          </Tabs>
        </Box>
        <TabPanel value={value} index={0} component={"div"}>
          {postDraftList.map((post) => (
            <PostCard
              key={post.id}
              postInfo={post}
              refreshPostList={findPostDraftList}
            />
          ))}
        </TabPanel>
        <TabPanel value={value} index={1} component={"div"}>
          {postPublishedList.map((post) => (
            <PostCard
              key={post.id}
              postInfo={post}
              refreshPostList={findPostPublishedList}
            />
          ))}
        </TabPanel>
        <TabPanel value={value} index={2} component={"div"}>
          Item Three
        </TabPanel>
      </Box>
    </div>
  );
};

export default PostTab;
