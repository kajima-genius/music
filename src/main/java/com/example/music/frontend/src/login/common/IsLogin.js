export const isLogin = () => {
    if(localStorage.getItem("user")) {
        return true;
    }

    return false;
}