
export default function user() {
    const user = JSON.parse(localStorage.getItem('user'));

    if (user) {
        return { 'Authorization': user.tokenType + " " + user.token };
    } else {
        return null;
    }
}