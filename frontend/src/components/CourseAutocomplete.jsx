import { useState } from "react";
import Autocomplete from "@mui/material/Autocomplete";
import TextField from "@mui/material/TextField";
import axios from "axios";

const CourseAutocomplete = () => {

  const [options, setOptions] = useState([]);

  const handleInputChange = async (event, value) => {

    if (value.length < 3) return;

    try {
      const response = await axios.get(
        `http://localhost:8080/api/courses/autocomplete/{keyword}`
      );

      setOptions(response.data);

    } catch (err) {
      console.error(err);
    }
  };

  return (
    <Autocomplete
      options={options}
      getOptionLabel={(option) => option.name}
      onInputChange={handleInputChange}
      renderInput={(params) => (
        <TextField {...params} label="Search course" />
      )}
    />
  );
};

export default CourseAutocomplete;