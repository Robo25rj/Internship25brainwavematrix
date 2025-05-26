



document.getElementById('loginForm').addEventListener('submit', async function(e) {
    e.preventDefault();
  
    const accountNo = document.getElementById('accountNumber').value;
     const pin = document.getElementById('pin').value.trim(); 
     console.log(accountNumber,pin);
    
    const response = await fetch("http://localhost:8080/api/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ accountNo, pin })
    });
  
    if (response.ok) {
      document.getElementById('loginForm').style.display = 'none';
      document.getElementById('dashboard').style.display = 'block';
    } else {
      alert('Login failed! Check your credentials.');
    }
  });
  
  function checkBalance() {
    fetch('http://localhost:8080/api/balance')
      .then(res => res.json())
      .then(data => {
        document.getElementById('actions').innerHTML = `<p>Your balance is ₹${data.balance}</p>`;
      });
  }
  
  function showDeposit() {
    document.getElementById('actions').innerHTML = `
      <input type="number" id="depositAmount" placeholder="Enter amount to deposit" />
      <button onclick="deposit()">Confirm Deposit</button>
    `;
  }
  
  function deposit() {
    const amount = document.getElementById('depositAmount').value;
    fetch('http://localhost:8080/api/deposit', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ amount })
    }).then(() => checkBalance());
  }
  
  function showWithdraw() {
    document.getElementById('actions').innerHTML = `
      <input type="number" id="withdrawAmount" placeholder="Enter amount to withdraw" />
      <button onclick="withdraw()">Confirm Withdraw</button>
    `;
  }
  
  function withdraw() {
    const amount = document.getElementById('withdrawAmount').value;
    fetch('http://localhost:8080/api/withdraw', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ amount })
    }).then(() => checkBalance());
  }
  
  function showHistory() {
    fetch('http://localhost:8080/api/history')
      .then(res => res.json())
      .then(data => {
        const list = data.map(txn => `<li>${txn}</li>`).join('');
        document.getElementById('actions').innerHTML = `<ul>${list}</ul>`;
      });
  }
  
  function logout() {
    document.getElementById('dashboard').style.display = 'none';
    document.getElementById('loginForm').style.display = 'block';
    document.getElementById('loginForm').reset();
    document.getElementById('actions').innerHTML = '';
  }
  