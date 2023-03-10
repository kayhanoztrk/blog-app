export const getAllCommentsByPostId = async (postId) => {
  try {
    const response = await fetch(`/api/v1/comment/view?postId=${postId}`, {
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

export const saveComment = async (comment) => {
  try {
    const response = await fetch("/api/v1/comment", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        Authorization: localStorage.getItem("tokenKey"),
      },
      body: JSON.stringify({
        postId: comment.postId,
        userId: comment.userId,
        text: comment.text,
      }),
    });
  } catch (e) {
    if (e == "Unauthorized") {
      console.log("e.message", e.message);
    }
    return [];
  }
};
