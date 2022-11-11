import React, {useEffect, useState} from 'react';
import {Cinema, Film, Session} from "../MovieTypes";
import {useHistory} from "react-router-dom";
import MovieApi from "../api/MovieApi";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import Select from 'react-select';

interface SessionFormProps {
    session: Session,
    setSession: (session: Session) => void,
    films: Film[],
    cinemaId: number
    cinemaRoomId: number
}

const SessionForm = ({session, setSession, films, cinemaId, cinemaRoomId}: SessionFormProps) => {
    const [hasChanged, setHasChanged] = useState<boolean>(false)
    const navigate = useHistory();


    const onChangedInput = (filed: string) => (e: any) => {
        let newItem = {...session}
        newItem[filed] = e.target.value
        setSession(newItem)
        setHasChanged(true)
    }
    const onChangedDatepicker = (filed: string) => (e: Date) => {
        let newItem = {...session}
        newItem[filed] = e.getTime()
        setSession(newItem)
        setHasChanged(true)
    }

    const onButtonClick = (e: any) => {
        e.preventDefault()
        let sendSession = {...session}
        sendSession.filmId = session.film.id
        sendSession.cinemaRoomId = cinemaRoomId
        sendSession.price = Number(session.price)
        MovieApi.saveSession(session.id, sendSession)
            .then(res => {
                let session = res as Session
                setSession(session)
                navigate.push('/cinema/' + cinemaId + '/session/' + cinemaRoomId + '/' + session.id)
            })
            .catch(err => navigate.push('/cinema/' + cinemaId + '/session/' + cinemaRoomId))
    }
    return (
        <>
            <form className="w-full p-5">
                <div className="flex flex-wrap -mx-3 mb-6">
                    <div className="w-full md:w-1/2 px-3 mb-6 md:mb-0">
                        <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                               htmlFor="grid-city">
                            Film
                        </label>
                        <Select id="films"
                                options={films.map(film => {
                                    return {
                                        label: film.name,
                                        value: film.id
                                    }
                                })}
                                isSearchable={false}
                                value={{
                                    value: session.film.id,
                                    label: session.film.name,
                                }}
                                onChange={(e: any) => {
                                    let newSession = {...session}
                                    newSession.film = films.find(value => value.id === e.value)
                                    setSession(newSession)
                                }}

                                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                    </div>
                    <div className="w-full md:w-1/2 px-3 ">
                        <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                               htmlFor="grid-city">
                            Date time
                        </label>
                        <DatePicker selected={new Date(session.date)}
                                    onChange={onChangedDatepicker('date')}
                                    showTimeSelect
                                    dateFormat="yyyy.MM.dd hh:mm"
                                    timeFormat="HH:mm"/>
                    </div>
                    <div className="w-full md:w-1/2 px-3">

                        <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                               htmlFor="grid-last-name">
                            Price
                        </label>
                        <input
                            className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                            id="grid-last-name"
                            type="text"
                            placeholder="Doe"
                            value={session.price}
                            onChange={onChangedInput('price')}>
                        </input>
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

export default SessionForm;