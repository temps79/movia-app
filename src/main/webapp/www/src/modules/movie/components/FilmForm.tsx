import React, {useState} from 'react';
import {Film} from "../MovieTypes";
import {useHistory} from "react-router-dom";
import MovieApi from "../api/MovieApi";

interface FilmFormProps {
    film: Film,
    setFilm: (film: Film) => void,
    isLoading: boolean,
    setIsLoading: (isLoading: boolean) => void,
}

const FilmForm = ({film, setFilm, isLoading, setIsLoading}: FilmFormProps) => {
    const [hasChanged, setHasChanged] = useState<boolean>(false)
    const navigate = useHistory();

    const onChangedInput = (filed: string) => (e: any) => {
        let newItem = {...film}
        newItem[filed] = e.target.value
        setFilm(newItem)
        setHasChanged(true)
    }
    const onChangedCheckbox = (filed: string) => (e: any) => {
        let newItem = {...film}
        newItem[filed] = !film[filed]
        setFilm(newItem)
        setHasChanged(true)
    }
    const onButtonClick = (e: any) => {
        e.preventDefault()
        MovieApi.saveFilm(film.id, film)
            .then(res => {
                let newCinema = res as Film
                setFilm(newCinema)
                navigate.push('/film/' + newCinema.id)
            })
            .catch(err => navigate.push('/'))
    }
    return (
        <>
            <form className="w-full p-5">
                <div className="flex flex-wrap -mx-3 mb-6">
                    <div className="w-full md:w-1/2 px-3 mb-6 md:mb-0">
                        <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                               htmlFor="grid-city">
                            Name
                        </label>
                        <input
                            className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                            id="grid-city"
                            type="text"
                            placeholder="Film name"
                            value={film.name}
                            onChange={onChangedInput('name')}>
                        </input>
                    </div>
                    <div className="w-full md:w-1/2 px-3">
                        <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                               htmlFor="grid-last-name">
                            Description
                        </label>
                        <input
                            className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                            id="grid-last-name"
                            type="text"
                            placeholder="Description"
                            value={film.description}
                            onChange={onChangedInput('description')}>
                        </input>
                    </div>
                </div>
                <div className="flex flex-wrap -mx-3 mb-6">
                    <div className="w-full md:w-1/2 px-3 mb-6 md:mb-0">
                        <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                               htmlFor="grid-city">
                            Image
                        </label>
                        <input
                            className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                            id="grid-city"
                            type="text"
                            placeholder="Image URL"
                            value={film.image}
                            onChange={onChangedInput('image')}>
                        </input>
                    </div>
                    <div className="w-full md:w-1/2 px-3">
                        <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                               htmlFor="grid-last-name">
                            Time
                        </label>
                        <input
                            className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                            id="grid-last-name"
                            type="number"
                            placeholder="Description"
                            value={film.time}
                            onChange={onChangedInput('time')}>
                        </input>
                    </div>
                    <div className="w-full md:w-1/2 px-3 flex items-center mt-5">
                        <input checked={film.isNew}
                               onClick={onChangedCheckbox('isNew')}
                               id="checked-checkbox"
                               type="checkbox"
                               className="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
                        </input>
                        <label htmlFor="checked-checkbox"
                               className="ml-2 text-sm font-medium text-gray-900 dark:text-gray-300">Is premiere</label>
                    </div>
                </div>
                <button
                    className={"bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full " + (!hasChanged ? 'cursor-not-allowed' : '')}
                    disabled={!hasChanged}
                    onClick={onButtonClick}>
                    Save
                </button>
            </form>
        </>
    );
};

export default FilmForm;