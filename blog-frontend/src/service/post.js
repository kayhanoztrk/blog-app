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

