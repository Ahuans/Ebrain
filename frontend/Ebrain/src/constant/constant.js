const API_METHOD = [
    {
        label:"GET",
        value:"get"
    },
    {
        label:"POST",
        value:"post"
    },
    {
        label:"DELETE",
        value:"delete"
    },
]


const HEADER_NAMES=[
    {value:"Content-Type"},
    {value:"Accept"},
    {value:"Authorization"},
    {value:"Accept-Encoding"},
    {value:"Accept-Language"},
    {value:"User-Agent"},
    {value:"If-Modified-Since"},

]

const API_SERVER_URL= "http://localhost:3000/api"


export {API_METHOD,API_SERVER_URL,HEADER_NAMES}