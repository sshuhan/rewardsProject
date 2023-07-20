
window.addEventListener('load', ()=> {

        const form = document.querySelector('.container_former');
        const input = document.querySelector('.container_former_input');
        const contents = document.querySelector('.container_content');

        form.addEventListener('submit', (e)=>{
                e.preventDefault();

                const id = input.value;

                if(!id){
                    alert("Please fill out ID");
                    return;
                }
                let rewardsContent = '';
                const url = 'http://localhost:8080/rewards/' + id;

                fetch(url).then(res => res.json()).then(data =>{
                    if(data.customerId){
                        rewardsContent += <li class="container_content_item">Customer ID: ${data.customerId}</li>
                        <li class="container_content_item">Last First Month Rewards: ${data.lastFirstMonthPoints}</li>
                        <li class="container_content_item">Last Second Month Rewards: ${data.lastSecondMonthPoints}</li>
                        <li class="container_content_item">Last Third Month Rewards: ${data.lastThirdMonthPoints}</li>
                        <li class="container_content_item">Last Three Month Rewards: ${data.totalPoints}</li>;
                    }else{
                        rewardsContent += <li class="container_content_item danger">Customer ID is not valid</li>;
                    }
                    contents.innerHTML = rewardsContent;
                });
            }

        )
    }
)

