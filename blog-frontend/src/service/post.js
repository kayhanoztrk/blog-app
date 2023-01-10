export const getAllPosts = async () => {
  try {
    const response = await fetch("/api/v1/post", {
        headers : { 
            'Content-Type': 'application/json',
            'Accept': 'application/json'
           }
    });
    return response.json();
  } catch (e) {
    console.log("errror", e.message);
    return [];
  }
};

export const getPostById = async (postId) => {
  try {
    const response = await fetch("/api/v1/post/" + postId);
    return response.json();
  } catch (e) {
    console.log("errror", e.message);
    return [];
  }
};

export const getAllPostsByUserId = async (userId) => {
    try {
      const response = await fetch("/api/v1/post/view?userId=" + userId);
      return response.json();
    } catch (e) {
      console.log("errror", e.message);
      return [];
    }
  };


export const savePost = async(post) => {
    try{
        const response = await fetch("/api/v1/post", {
            method: 'POST',
            headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                title: post.title,
                text: post.text,
                userId: post.userId
            })
        });

        return response.json();
    }catch(e){
        console.log('e.message', e.message);
        return [];
    }
}

