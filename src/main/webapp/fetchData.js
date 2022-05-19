const button = document.getElementById('change')
const info = document.getElementById('info')
const data = document.getElementById('data')

button.addEventListener('click',async () => {
    const resp = await fetch('http://localhost:8080/laba6_war_exploded/change')
    if(resp.ok){
        info.insertAdjacentText("afterend", 'success')
        data.insertAdjacentText("afterend", await resp.text())

    } else {
        info.insertAdjacentText("afterend", 'error' + resp.status)
    }
})