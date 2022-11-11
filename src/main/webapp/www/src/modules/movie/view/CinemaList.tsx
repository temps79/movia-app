import React, {useState} from 'react';
import {Cinema} from "../MovieTypes";
import {Link, useHistory} from "react-router-dom";
import {useCookies} from "react-cookie";
import MovieApi from "../api/MovieApi";

interface CinemaListProps {
    cinemas: Cinema[],
    setUpdateTime: () => void
}

const CinemaList = ({cinemas, setUpdateTime}: CinemaListProps) => {
    const [searchInput, setSearchInput] = useState<string>('')
    const [cookies, setCookie, removeCookie] = useCookies(['lvl']);
    const [isAdmin, setIsAdmin] = useState<boolean>(!!cookies.lvl && Number(cookies.lvl) === 100)
    const [isUser, setIsUser] = useState<boolean>(!!cookies.lvl && Number(cookies.lvl) === 10)
    const navigate = useHistory();


    const onChangeInput = (e: any) => {
        setSearchInput(e.target.value)
    }
    return (
        <section>
            <header className="bg-white space-y-4 p-4 sm:px-8 sm:py-6 lg:p-4 xl:px-8 xl:py-6">
                <div className="flex items-center justify-between">
                    <h2 className="font-semibold text-slate-900">Cinemas</h2>
                    {(isUser || isAdmin) ?
                        <div className={'flex'}>
                            {isAdmin &&
                                <Link to="/new_film"
                                      className="hover:bg-blue-400 group flex items-center rounded-md bg-blue-500 text-white text-sm font-medium pl-2 pr-3 py-2 shadow-sm">
                                    <svg width="20" height="20" fill="currentColor" className="mr-2" aria-hidden="true">
                                        <path
                                            d="M10 5a1 1 0 0 1 1 1v3h3a1 1 0 1 1 0 2h-3v3a1 1 0 1 1-2 0v-3H6a1 1 0 1 1 0-2h3V6a1 1 0 0 1 1-1Z"/>
                                    </svg>
                                    New film
                                </Link>
                            }
                            <button
                                className={"ml-5 bg-red-600 hover:bg-red-700 text-white font-bold py-2 px-4  rounded-md "}
                                onClick={(e: any) => {
                                    e.preventDefault()
                                    MovieApi.logout()
                                        .then(res => {
                                            removeCookie('lvl')
                                            setUpdateTime()
                                            navigate.push('/')
                                        })
                                }}>
                                Log out
                            </button>
                        </div>
                        :
                        <a href="/login"
                           className="hover:bg-green-400 group flex items-center rounded-md bg-green-500 text-white text-sm font-medium pl-2 pr-3 py-2 shadow-sm">
                            Sign in
                        </a>
                    }
                </div>
                <form className="group relative">
                    <svg width="20" height="20" fill="currentColor"
                         className="absolute left-3 top-1/2 -mt-2.5 text-slate-400 pointer-events-none group-focus-within:text-blue-500"
                         aria-hidden="true">
                        <path
                            d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"/>
                    </svg>
                    <input
                        className="focus:ring-2 focus:ring-blue-500 focus:outline-none appearance-none w-full text-sm leading-6 text-slate-900 placeholder-slate-400 rounded-md py-2 pl-10 ring-1 ring-slate-200 shadow-sm"
                        type="text" aria-label="Filter cinemas" placeholder="Filter cinemas..."
                        value={searchInput}
                        onChange={onChangeInput}
                    />
                </form>
            </header>
            <ul className="bg-slate-50 p-4 sm:px-8 sm:pt-6 sm:pb-8 lg:p-4 xl:px-8 xl:pt-6 xl:pb-8 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-1 xl:grid-cols-2 gap-4 text-sm leading-6">
                {cinemas.filter(value => searchInput.length > 2 ? value.city.includes(searchInput) : true)
                    .map(value =>
                        <CinemaListRow cinema={value}/>
                    )}
                {cinemas.length === 0 && !isAdmin && <div>Cinemas not found</div>}
                {isAdmin &&
                    <li className="flex">
                        <a href="/new_cinema"
                           className="hover:border-blue-500 hover:border-solid hover:bg-white hover:text-blue-500 group w-full flex flex-col items-center justify-center rounded-md border-2 border-dashed border-slate-300 text-sm leading-6 text-slate-900 font-medium py-3">
                            <svg className="group-hover:text-blue-500 mb-1 text-slate-400" width="20" height="20"
                                 fill="currentColor" aria-hidden="true">
                                <path
                                    d="M10 5a1 1 0 0 1 1 1v3h3a1 1 0 1 1 0 2h-3v3a1 1 0 1 1-2 0v-3H6a1 1 0 1 1 0-2h3V6a1 1 0 0 1 1-1Z"/>
                            </svg>
                            New Cinema
                        </a>
                    </li>}

            </ul>
        </section>
    );
};

export default CinemaList;

interface CinemaListRowProps {
    cinema: Cinema
}

const CinemaListRow = ({cinema}: CinemaListRowProps) => {

    return (
        <li className={'group cursor-pointer rounded-md p-3 bg-white ring-1 ring-slate-200 shadow-sm hover:bg-blue-500 hover:ring-blue-500 hover:shadow-md dark:bg-slate-700 dark:ring-0 dark:highlight-white/10 dark:hover:bg-blue-500'}>
            <Link to={`cinema/${cinema.id}`}>
                <dl className="grid sm:block lg:grid xl:block grid-cols-2 grid-rows-2 items-center">
                    <div>
                        <dt className="sr-only">City</dt>
                        <dd className="group-hover:text-white font-semibold text-slate-900">
                            {cinema.city}
                        </dd>
                    </div>
                    <div>
                        <dt className="sr-only">Address</dt>
                        <dd className="group-hover:text-blue-200">
                            {cinema.address}
                        </dd>
                    </div>
                </dl>
            </Link>
        </li>
    )
}