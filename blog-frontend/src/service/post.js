export const getAllPostsPublished = async () => {
  try {
    const response = await fetch("/api/v1/post/published", {
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
        Authorization: localStorage.getItem("tokenKey"),
      },
    });
    return response.json();
  } catch (e) {
    console.log("errror", e.message);
    return [];
  }
};

export const getPostPublishedList = async (userId) => {
  try {
    const response = await fetch("/api/v1/post/published/user/" + userId, {
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
        Authorization: localStorage.getItem("tokenKey"),
      },
    });
    return response.json();
  } catch (e) {
    console.log("errror", e.message);
    return [];
  }
};

export const getPostDraftList = async (userId) => {
  try {
    const response = await fetch("/api/v1/post/draft/user/" + userId, {
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
        Authorization: localStorage.getItem("tokenKey"),
      },
    });
    return response.json();
  } catch (e) {
    console.log("errror", e.message);
    return [];
  }
};

export const getPostCommentedList = async (userId) => {
  try {
    const response = await fetch(
      "/api/v1/post/mostCommented?userId=" + userId,
      {
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
          Authorization: localStorage.getItem("tokenKey"),
        },
      }
    );
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

export const savePost = async (post) => {
  try {
    const response = await fetch("/api/v1/post", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        Authorization: localStorage.getItem("tokenKey"),
      },
      body: JSON.stringify({
        title: post.title,
        text: post.text,
        tagList: post.tags,
        userId: post.userId,
      }),
    });
    return response.json();
  } catch (e) {
    console.log("e.message", e.message);
    return [];
  }
};

export const updatePostById = async (postId, post) => {
  try {
    const response = await fetch("/api/v1/post/" + postId, {
      method: "PUT",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        Authorization: localStorage.getItem("tokenKey"),
      },
      body: JSON.stringify({
        title: post.title,
        text: post.text,
        isPublished: post.isPublished,
        userId: post.userId,
      }),
    });

    return response.json();
  } catch (e) {
    console.log("e.message", e.message);
    return [];
  }
};

export const deletePostById = async (postId) => {
  try {
    const response = await fetch(`/api/v1/post/${postId}`, {
      method: "DELETE",
      headers: {
        Accept: "application/json",
        Authorization: localStorage.getItem("tokenKey"),
      },
    });
    return response;
  } catch (e) {
    console.log("e.message", e.message);
    return [];
  }
};
