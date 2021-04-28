export const getCurrentUser = () => {
  const userStr = localStorage.getItem("user");
  if (userStr) {
    return JSON.parse(userStr);
  } else return null;
};

export const getToken = () => {
  return localStorage.getItem("token") || null;

};

export const setUserSession = (token, user, isAuthenticated) => {
  localStorage.setItem("token", token);
  localStorage.setItem("user", JSON.stringify(user));
  localStorage.setItem("isAuthenticated", isAuthenticated);
};

export const removeUserSession = () => {
  localStorage.removeItem("token");
  localStorage.removeItem("user");
  localStorage.removeItem("isAuthenticated");
};

export const authHeader = () => {
  const user = JSON.parse(localStorage.getItem("user"));

  if (user) {
    return {Authorization: 'Bearer ' + localStorage.getItem('token')};
  } else {
    return {};
  }
};
