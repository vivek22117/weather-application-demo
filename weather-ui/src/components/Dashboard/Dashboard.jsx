import React, {forwardRef, useEffect, useState} from "react";
import axios from "axios";
import {AlertMsg} from "../index";
import {Grid} from '@material-ui/core';
import MaterialTable from "material-table";
import ArrowDownward from '@material-ui/icons/ArrowDownward';
import Check from '@material-ui/icons/Check';
import ChevronLeft from '@material-ui/icons/ChevronLeft';
import ChevronRight from '@material-ui/icons/ChevronRight';
import Clear from '@material-ui/icons/Clear';
import DeleteOutline from '@material-ui/icons/DeleteOutline';
import Edit from '@material-ui/icons/Edit';
import FilterList from '@material-ui/icons/FilterList';
import FirstPage from '@material-ui/icons/FirstPage';
import LastPage from '@material-ui/icons/LastPage';
import Remove from '@material-ui/icons/Remove';
import SaveAlt from '@material-ui/icons/SaveAlt';
import Search from '@material-ui/icons/Search';
import ViewColumn from '@material-ui/icons/ViewColumn';
import AddBox from '@material-ui/icons/AddBox'

import styles from './Dashboard.module.css';

import {getCurrentUser, getToken} from "../../Utils/Common";
import {useHistory} from "react-router-dom";


const tableIcons = {
  Add: forwardRef((props, ref) => <AddBox {...props} ref={ref}/>),
  Check: forwardRef((props, ref) => <Check {...props} ref={ref}/>),
  Clear: forwardRef((props, ref) => <Clear {...props} ref={ref}/>),
  Delete: forwardRef((props, ref) => <DeleteOutline {...props} ref={ref}/>),
  DetailPanel: forwardRef((props, ref) => <ChevronRight {...props} ref={ref}/>),
  Edit: forwardRef((props, ref) => <Edit {...props} ref={ref}/>),
  Export: forwardRef((props, ref) => <SaveAlt {...props} ref={ref}/>),
  Filter: forwardRef((props, ref) => <FilterList {...props} ref={ref}/>),
  FirstPage: forwardRef((props, ref) => <FirstPage {...props} ref={ref}/>),
  LastPage: forwardRef((props, ref) => <LastPage {...props} ref={ref}/>),
  NextPage: forwardRef((props, ref) => <ChevronRight {...props} ref={ref}/>),
  PreviousPage: forwardRef((props, ref) => <ChevronLeft {...props} ref={ref}/>),
  ResetSearch: forwardRef((props, ref) => <Clear {...props} ref={ref}/>),
  Search: forwardRef((props, ref) => <Search {...props} ref={ref}/>),
  SortArrow: forwardRef((props, ref) => <ArrowDownward {...props} ref={ref}/>),
  ThirdStateCheck: forwardRef((props, ref) => <Remove {...props} ref={ref}/>),
  ViewColumn: forwardRef((props, ref) => <ViewColumn {...props} ref={ref}/>)
};

const Dashboard = (props) => {
  const history = useHistory();

  console.log("In Dashboard " + props.isAuthenticated);

  const columns = [
    {
      title: "City", field: "cityName", align: "center",
      cellStyle: {
        backgroundColor: "#039be5",
        color: "#FFF"
      },
      headerStyle: {
        backgroundColor: '#039be5',
      }
    },
    {title: "Current Temperature", field: "currentTemperature", align: "center"},
    {title: "Max Temperature", field: "maxTemperature", align: "center"},
    {title: "Min Temperature", field: "minTemperature", align: "center"},
    {title: "Sunrise", field: "sunrise", align: "center"},
    {title: "Sunset", field: "sunset", align: "center"},
    {title: "Description", field: "weatherDescription", align: "center"}
  ];
  const [weatherResponse, setWeatherResponse] = useState({});
  const [weatherData, setWeatherData] = useState([]);
  const [query, setQuery] = useState("");
  const [alertData, setAlertMsg] = useState({alert: "", isDashboard: true});

  const config = {
    headers: {
      Authorization: 'Bearer ' + getToken()
    }
  };

  useEffect(() => {
    if (getCurrentUser()) {
      console.log("Weather history API...");

      async function fetchWeatherHistory() {
        const result = await axios.get('api/weather/history?username=' + getCurrentUser(), config);
        setWeatherData(result.data.weatherDataDTOList);
      }

      fetchWeatherHistory();
    }

  }, []);

  const handleRowDelete = async (oldData, resolve) => {

    console.log(oldData);
    console.log(resolve);

    axios.delete('api/weather/delete?city=' + oldData.cityName + '&username=' + getCurrentUser(), config)
      .then(res => {
        const dataDelete = [...weatherData];
        const index = oldData.tableData.id;
        dataDelete.splice(index, 1);
        setWeatherData([...dataDelete]);
        resolve()
      })
      .catch(error => {
        console.log(error);
        setAlertMsg({alert: error.message, isDashboard: true});
        resolve()
      })
  };

  const getData = async () => {
    if (query !== "") {
      axios.get('api/weather/' + query, config)
        .then(res => {
          console.log(res);
          if (!res.data.weatherDataDTO) {
            setAlertMsg({alert: "No weather data for city name " + query, isDashboard: true});
          }
          setWeatherResponse(res.data.weatherDataDTO);
          setWeatherData(res.data.weatherDataDTOList);
          console.log(weatherData);
          setQuery("");
        })
        .catch(err => {
          console.log(err);
          setAlertMsg({alert: err.response.data, isDashboard: true});
        });
    } else {
      setAlertMsg({alert: "Please provide search city name " + query, isDashboard: true});
    }
  };

  const onSubmit = e => {
    e.preventDefault();
    getData();
  };

  return (
    <div className={styles.search}>
      <h1>Search Weather Data By City</h1>
      <form className={styles.searchForm}>
        {alertData.alert !== "" && <AlertMsg alert={alertData}/>}
        <input
          type="text"
          name="query"
          onChange={(e) => setQuery(e.target.value)}
          value={query}
          autoComplete="off"
          placeholder="City Name"
        />
        <input onClick={(e) => onSubmit(e)} type="submit" value="Search"/>
      </form>
      {weatherData.length !== 0 &&
      <div className={styles.table}>
        <Grid container spacing={1}>
          <Grid>
            {alertData.alert !== "" && <AlertMsg alert={alertData}/>}
            <MaterialTable
              options={{
                search: true,
                headerStyle: {
                  backgroundColor: '#01579b',
                  color: '#FFF',

                }
              }}
              title="Weather Data Search History"
              columns={columns}
              data={weatherData}
              icons={tableIcons}
              editable={{
                onRowDelete: (oldData) =>
                  new Promise((resolve) => {
                    handleRowDelete(oldData, resolve)
                  }),
              }}
            />
          </Grid>
        </Grid>
      </div>
      }

    </div>
  );

};

export default Dashboard;