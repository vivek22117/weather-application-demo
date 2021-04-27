import React, {useState} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';

import styles from './Management.module.css';
import {AlertMsg} from "../index";
import axios from "axios";
import {getToken} from "../../Utils/Common";
import {Grid} from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
  button: {
    display: 'flex',
    alignItems: 'center',
    marginTop: theme.spacing(5),
  },
  formControl: {
    margin: theme.spacing(1),
    minWidth: 120,
  },
}));

const Management = () => {
  const columns = [
    {
      title: "Name", align: "center",
      cellStyle: {
        backgroundColor: "#039be5",
        color: "#FFF"
      },
      headerStyle: {
        backgroundColor: '#039be5',
      }
    }
  ];

  const classes = useStyles();
  const [category, setCategory] = useState('');
  const [open, setOpen] = useState(false);
  const [query, setQuery] = useState("");
  const [searchType, setSearchType] = useState('');
  const [alertData, setAlertMsg] = useState({alert: "", isDashboard: true});
  const [url, setUrl] = useState('');
  const [term, setTerm] = useState('');

  const [responseData, setResponseData] = useState([]);

  const handleChange = (event) => {
    setCategory(event.target.value);
    console.log(event.target.value);
    if (event.target.value === "city") {
      setSearchType('city');
      setUrl('api/admin/user/history?');
      setTerm('Provide city name');
    } else {
      setSearchType('username')
      setUrl('api/admin/city/history?');
      setTerm('Provide user email');
    }
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleOpen = () => {
    setOpen(true);
  };

  const handleQuery = (event) => {
    setQuery(event.target.value);
  };


  const config = {
    headers: {
      Authorization: 'Bearer ' + getToken()
    }
  };

  const getSearchData = async () => {

    console.log(searchType);
    console.log(query);

    if (query !== "") {
      axios.get(url + searchType + '=' + query, config)
        .then(res => {
          console.log(res.data);
          if (searchType === "city") {
            setResponseData(res.data.profileList);
          }
          if (searchType === "username") {
            setResponseData(res.data.cityList);
          }
          setQuery("");
        })
        .catch(err => {
          console.log(err);
          setAlertMsg({alert: err.message, isDashboard: false});
        });
    } else {
      setAlertMsg({alert: "Please provide search term!" + query, isDashboard: true});
    }
  };

  const onSubmit = e => {
    e.preventDefault();
    getSearchData();
  };

  return (
    <div className={styles.search}>
      <FormControl className={classes.formControl}>
        <InputLabel id="demo-controlled-open-select-label">Category</InputLabel>
        <Select
          id="Search history for user & city"
          open={open}
          onClose={handleClose}
          onOpen={handleOpen}
          value={category}
          onChange={handleChange}
        >
          <MenuItem value={"city"}>City Name</MenuItem>
          <MenuItem value={"username"}>User Name</MenuItem>
        </Select>
      </FormControl>

      <h1>Search Pattern By City & Weather</h1>
      {alertData.alert !== "" && <AlertMsg alert={alertData}/>}
      <form className={styles.searchForm}>
        <input
          type="text"
          name="query"
          onChange={handleQuery}
          value={query}
          autoComplete="off"
          placeholder={term}
        />
        <input onClick={(e) => onSubmit(e)} type="submit" value="Search"/>
      </form>
      {responseData.length !== 0 &&
      <div className={styles.table}>
        <Grid container spacing={1}>
          <Grid>
            <table>
              {responseData.map((item) => (
                <tr key={item.id}>{item}</tr>
              ))}
            </table>
          </Grid>
        </Grid>
        {alertData.alert !== "" && <AlertMsg alert={alertData}/>}
      </div>
      }

    </div>
  )
};

export default Management;

