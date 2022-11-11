import React, {useState} from 'react';
import {Cinema} from "../MovieTypes";
import {useHistory} from "react-router-dom";
import MovieApi from "../api/MovieApi";
import {useCookies} from "react-cookie";

interface CinemaFormProps {
    cinema: Cinema,
    setCinema: (cinema: Cinema) => void,
    isLoading: boolean,
    setIsLoading: (isLoading: boolean) => void,
    updateList: () => void
}

const CinemaForm = ({cinema, setCinema, isLoading, setIsLoading, updateList}: CinemaFormProps) => {
    const [hasChanged, setHasChanged] = useState<boolean>(false)
    const navigate = useHistory();
    const [cookies, setCookie, removeCookie] = useCookies(['lvl']);
    const [isAdmin, setIsAdmin] = useState<boolean>(!!cookies.lvl && Number(cookies.lvl) === 100)

    const onChangedInput = (filed: string) => (e: any) => {
        let newItem = {...cinema}
        newItem[filed] = e.target.value
        setCinema(newItem)
        setHasChanged(true)
    }
    const onButtonClick = (e: any) => {
        e.preventDefault()
        MovieApi.saveCinema(cinema.id, cinema)
            .then(res => {
                let newCinema = res as Cinema
                setCinema(newCinema)
                updateList()
                navigate.push('/cinema/' + newCinema.id)
            })
    }
    return (
        <>
            <form className="w-full p-5">
                <div className="flex flex-wrap -mx-3 mb-6">
                    <div className="w-full md:w-1/2 px-3 mb-6 md:mb-0">
                        <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                               htmlFor="grid-city">
                            City
                        </label>
                        <input
                            className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                            id="grid-city"
                            type="text"
                            placeholder="Albuquerque"
                            value={cinema.city}
                            onChange={onChangedInput('city')}>
                        </input>
                    </div>
                    <div className="w-full md:w-1/2 px-3">
                        <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                               htmlFor="grid-last-name">
                            Address
                        </label>
                        <input
                            className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                            id="grid-last-name"
                            type="text"
                            placeholder="Doe"
                            value={cinema.address}
                            onChange={onChangedInput('address')}>
                        </input>
                    </div>
                </div>
                {isAdmin &&
                    <button
                        className={"bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full " + (!hasChanged ? 'cursor-not-allowed' : '')}
                        disabled={!hasChanged}
                        onClick={onButtonClick}>
                        Save
                    </button>
                }

            </form>
        </>
    );
};

export default CinemaForm;