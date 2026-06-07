// ─── Problem Solve Toggle ──────────────────────────────────────────────────
async function toggleSolve(id, currentlySolved) {
  const endpoint = currentlySolved
    ? `/api/problems/${id}/unsolved`
    : `/api/problems/${id}/solve`;

  let params = '';
  if (!currentlySolved) {
    const timeTaken = prompt('Time taken (minutes)?', '30');
    const notes     = prompt('Any notes / approach?', '');
    params = `?timeTaken=${timeTaken || 0}&notes=${encodeURIComponent(notes || '')}`;
  }

  const res = await fetch(endpoint + params, { method: 'POST' });
  if (res.ok) window.location.reload();
  else alert('Something went wrong. Try again.');
}

// ─── Filter Form Auto-Submit ───────────────────────────────────────────────
document.querySelectorAll('.auto-submit').forEach(el => {
  el.addEventListener('change', () => el.closest('form').submit());
});

// ─── Interview Countdown Timer ─────────────────────────────────────────────
let timerInterval = null;
let timerSeconds  = 0;
let timerRunning  = false;

function startTimer(durationMinutes) {
  if (timerRunning) return;
  timerSeconds = (durationMinutes || 60) * 60;
  timerRunning = true;
  timerInterval = setInterval(() => {
    if (timerSeconds <= 0) { clearInterval(timerInterval); timerRunning = false; alert("⏰ Time's up!"); return; }
    timerSeconds--;
    updateTimerDisplay();
  }, 1000);
}

// Complete/pause/reset timer functions
function pauseTimer() {
  clearInterval(timerInterval);
  timerRunning = false;
}

function resetTimer() {
  clearInterval(timerInterval);
  timerRunning = false;
  timerSeconds = 0;
  updateTimerDisplay();
}

function updateTimerDisplay() {
  const el = document.getElementById('timer-display');
  if (!el) return;
  const h = Math.floor(timerSeconds / 3600);
  const m = Math.floor((timerSeconds % 3600) / 60);
  const s = timerSeconds % 60;
  el.textContent = (h > 0 ? `${pad(h)}:` : '') + `${pad(m)}:${pad(s)}`;
}

function pad(n) { return String(n).padStart(2, '0'); }

// ─── Complete Interview Modal ──────────────────────────────────────────────
function openCompleteModal(id) {
  document.getElementById('complete-id').value = id;
  document.getElementById('complete-modal').style.display = 'flex';
}
function closeCompleteModal() {
  document.getElementById('complete-modal').style.display = 'none';
}

// ─── Company filter in company detail ─────────────────────────────────────
function filterCompanyProblems(company) {
  fetch(`/api/companies/${company}/problems`)
    .then(r => r.json())
    .then(problems => {
      const tbody = document.getElementById('company-problems-body');
      if (!tbody) return;
      tbody.innerHTML = problems.map(p => `
        <tr class="${p.solved ? 'solved-row' : ''}">
          <td><a class="title-link" href="${p.leetcodeLink}" target="_blank">${p.title}</a></td>
          <td><span class="badge badge-${p.difficulty.toLowerCase()}">${p.difficulty}</span></td>
          <td>${p.topic.replace(/_/g,' ')}</td>
          <td><span class="badge ${p.solved ? 'badge-solved' : 'badge-unsolved'}">${p.solved ? '✓ Solved' : 'Unsolved'}</span></td>
          <td><button class="btn btn-sm ${p.solved ? 'btn-danger' : 'btn-success'}" onclick="toggleSolve('${p.id}', ${p.solved})">
            ${p.solved ? 'Unmark' : 'Solve'}
          </button></td>
        </tr>`).join('');
    });
}

// ─── Search debounce ───────────────────────────────────────────────────────
let searchDebounce = null;
function onSearch(input) {
  clearTimeout(searchDebounce);
  searchDebounce = setTimeout(() => input.closest('form').submit(), 400);
}

// ─── 3D Constellation Background ──────────────────────────────────────────
function init3DBackground() {
  const canvas = document.createElement('canvas');
  canvas.id = 'bg-canvas';
  canvas.style.position = 'fixed';
  canvas.style.top = '0';
  canvas.style.left = '0';
  canvas.style.width = '100vw';
  canvas.style.height = '100vh';
  canvas.style.zIndex = '-1';
  canvas.style.pointerEvents = 'none';
  document.body.prepend(canvas);

  const ctx = canvas.getContext('2d');
  let width = (canvas.width = window.innerWidth);
  let height = (canvas.height = window.innerHeight);

  window.addEventListener('resize', () => {
    width = canvas.width = window.innerWidth;
    height = canvas.height = window.innerHeight;
  });

  const particleCount = 60;
  const particles = [];
  const maxDistance = 140;
  
  // Perspective parameters
  const focalLength = 320;
  const cameraDistance = 250;

  // Particle class with 3D coordinates
  class Particle3D {
    constructor() {
      this.x = (Math.random() - 0.5) * 550;
      this.y = (Math.random() - 0.5) * 550;
      this.z = (Math.random() - 0.5) * 550;
      this.vx = (Math.random() - 0.5) * 0.7;
      this.vy = (Math.random() - 0.5) * 0.7;
      this.vz = (Math.random() - 0.5) * 0.7;
      this.baseRadius = 1.5 + Math.random() * 2;
    }

    update() {
      this.x += this.vx;
      this.y += this.vy;
      this.z += this.vz;

      // Wrap around bounds in 3D space
      if (this.x < -275 || this.x > 275) this.vx *= -1;
      if (this.y < -275 || this.y > 275) this.vy *= -1;
      if (this.z < -275 || this.z > 275) this.vz *= -1;
    }

    project() {
      // Perspective projection calculation
      const scale = focalLength / (this.z + cameraDistance + 300);
      return {
        x: width / 2 + this.x * scale,
        y: height / 2 + this.y * scale,
        radius: this.baseRadius * scale,
        depth: this.z
      };
    }
  }

  // Initialize particles
  for (let i = 0; i < particleCount; i++) {
    particles.push(new Particle3D());
  }

  // Mouse rotation control
  let targetRotX = 0;
  let targetRotY = 0;
  let rotX = 0;
  let rotY = 0;

  window.addEventListener('mousemove', (e) => {
    targetRotY = ((e.clientX / width) - 0.5) * 0.25;
    targetRotX = -((e.clientY / height) - 0.5) * 0.25;
  });

  function rotate3D(p, angleX, angleY) {
    // Rotate Y
    let cosY = Math.cos(angleY), sinY = Math.sin(angleY);
    let x1 = p.x * cosY - p.z * sinY;
    let z1 = p.z * cosY + p.x * sinY;

    // Rotate X
    let cosX = Math.cos(angleX), sinX = Math.sin(angleX);
    let y2 = p.y * cosX - z1 * sinX;
    let z2 = z1 * cosX + p.y * sinX;

    return { x: x1, y: y2, z: z2 };
  }

  function animate() {
    ctx.clearRect(0, 0, width, height);

    // Interpolate rotation values
    rotX += (targetRotX - rotX) * 0.05;
    rotY += (targetRotY - rotY) * 0.05;

    const autoAngle = Date.now() * 0.0001;
    const projected = [];

    particles.forEach(p => {
      p.update();
      const rotated = rotate3D(p, rotX + Math.sin(autoAngle) * 0.05, rotY + autoAngle);
      const tempP = { x: rotated.x, y: rotated.y, z: rotated.z, baseRadius: p.baseRadius };
      const proj = p.project.call(tempP);
      projected.push(proj);
    });

    const isLightMode = document.body.classList.contains('light-mode');
    const colorRGB = isLightMode ? '14, 165, 233' : '16, 185, 129'; // Sky blue in light mode, emerald in dark mode

    // Draw lines
    for (let i = 0; i < projected.length; i++) {
      for (let j = i + 1; j < projected.length; j++) {
        const p1 = projected[i];
        const p2 = projected[j];
        
        const dx = p1.x - p2.x;
        const dy = p1.y - p2.y;
        const dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < maxDistance) {
          const opacity = (1 - dist / maxDistance) * 0.12;
          ctx.strokeStyle = `rgba(${colorRGB}, ${opacity})`;
          ctx.lineWidth = 0.5;
          ctx.beginPath();
          ctx.moveTo(p1.x, p1.y);
          ctx.lineTo(p2.x, p2.y);
          ctx.stroke();
        }
      }
    }

    // Draw nodes
    projected.forEach(p => {
      if (p.radius > 0) {
        ctx.fillStyle = isLightMode ? 'rgba(14, 165, 233, 0.3)' : 'rgba(16, 185, 129, 0.3)';
        ctx.beginPath();
        ctx.arc(p.x, p.y, p.radius, 0, Math.PI * 2);
        ctx.fill();
      }
    });

    requestAnimationFrame(animate);
  }

  animate();
}

// ─── Initialize on DOM Load ───────────────────────────────────────────────
document.addEventListener('DOMContentLoaded', () => {
  // 1. Animate progress bars
  document.querySelectorAll('.progress-fill[data-width]').forEach(el => {
    setTimeout(() => { el.style.width = el.dataset.width + '%'; }, 200);
  });
  updateTimerDisplay();

  // 2. Setup theme toggle
  const activityBottom = document.querySelector('.activity-bottom');
  if (activityBottom) {
    const themeBtn = document.createElement('button');
    themeBtn.id = 'theme-toggle';
    themeBtn.className = 'activity-btn';
    themeBtn.setAttribute('title', 'Toggle Theme');
    themeBtn.innerHTML = '🌙';
    
    activityBottom.appendChild(themeBtn);
    
    // Toggle Logic
    const currentTheme = localStorage.getItem('theme') || 'dark';
    if (currentTheme === 'light') {
      document.body.classList.add('light-mode');
      themeBtn.innerHTML = '☀️';
    }
    
    themeBtn.addEventListener('click', () => {
      if (document.body.classList.contains('light-mode')) {
        document.body.classList.remove('light-mode');
        localStorage.setItem('theme', 'dark');
        themeBtn.innerHTML = '🌙';
      } else {
        document.body.classList.add('light-mode');
        localStorage.setItem('theme', 'light');
        themeBtn.innerHTML = '☀️';
      }
    });
  }

  // 3. Setup dynamic 3D constellation background
  init3DBackground();
});
