const fs = require('fs');

// Path to the environment files
const targetPathDev = './src/environments/environment.ts';
const targetPathProd = './src/environments/environment.prod.ts';

// Get API_URL from environment variable, or default to http://localhost:8080
const apiUrl = process.env.API_URL || 'http://localhost:8080';

// Define the content for the environment files
const envConfigFileDev = `export const environment = {
  production: false,
  apiUrl: '${apiUrl}'
};
`;

const envConfigFileProd = `export const environment = {
  production: true,
  apiUrl: '${apiUrl}'
};
`;

console.log(`Setting API URL to: ${apiUrl}`);

// Write the files
fs.writeFile(targetPathDev, envConfigFileDev, function (err) {
  if (err) {
    console.error('Error writing environment.ts:', err);
  } else {
    console.log(`Successfully generated ${targetPathDev}`);
  }
});

fs.writeFile(targetPathProd, envConfigFileProd, function (err) {
  if (err) {
    console.error('Error writing environment.prod.ts:', err);
  } else {
    console.log(`Successfully generated ${targetPathProd}`);
  }
});
