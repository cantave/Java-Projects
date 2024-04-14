const yehURL = "https://api.kanye.rest/";

document.addEventListener("DOMContentLoaded", () => {
  const message = document.getElementById("message");
  const button = document.getElementById("randomButton");
  button.addEventListener("click", () => {
    fetch(yehURL)
      .then((httpResponse) => httpResponse.json())
      .then((data) => {
        message.innerText = data.quote;
      })
      .catch((error) => {
        console.error('Error', error);
        message.innerText = 'Failed to fetch Yeezus Quote';
      });
  });
});
