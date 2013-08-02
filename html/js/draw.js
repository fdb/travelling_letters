var tl = tl || {};

tl.settings = {
  message: 'hello',
  dx: 0.0,
  dy: 0.0,
  zoom: 100.0
};

tl.drawPolygon = function(ctx, coords) {
  ctx.beginPath();
  for (var i = 0; i < coords.length; i++) {
    var c = coords[i];
    var x = c[0];
    var y = c[1];
    if (i === 0) {
      ctx.moveTo(x + tl.settings.dx * Math.random(), y + tl.settings.dy * Math.random());
    } else {
      ctx.lineTo(x + tl.settings.dx * Math.random(), y + tl.settings.dy * Math.random());
    }
  }
  ctx.stroke();
};

tl.drawString = function(ctx, text) {
  _.each(text, function(letter) {
    tl.drawPolygon(ctx, tl.letters[letter]);
    ctx.translate(100, 0);
  });
};

tl.draw = function() {
  var c = document.getElementById('c');
  var ctx = c.getContext('2d');
  ctx.fillStyle = 'rgba(255, 255, 255, 0.1)';
  ctx.fillRect(0, 0, 800, 600);
  //ctx.clearRect(0, 0, 800, 600);
  ctx.save();
  ctx.translate(10, 10);
  ctx.scale(tl.settings.zoom / 100.0, tl.settings.zoom / 100.0);
  ctx.translate(10, 10);
  tl.drawString(ctx, tl.settings.message);  
  ctx.restore();
};

tl.update = function() {
    requestAnimationFrame(tl.update);
    tl.draw();
};


window.onload = function() {
  tl.gui = new dat.GUI();
  tl.gui.add(tl.settings, 'message');
  tl.gui.add(tl.settings, 'dx', -10, 10);
  tl.gui.add(tl.settings, 'dy', -10, 10);
  tl.gui.add(tl.settings, 'zoom', 10, 500);
  tl.update();
};

