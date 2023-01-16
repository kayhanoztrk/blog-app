export const sendRequest = async (process, userInfo) => {
  try {
    const response = await fetch("/api/v1/auth/" + process, {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: userInfo.username,
        password: userInfo.password,
      }),
    });

    return response.json();
  } catch (e) {
    console.log("error.message", e);
    return [];
  }
};
