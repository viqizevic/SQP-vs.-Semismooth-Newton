function plotf(a,b,gap)
  f = @(x) cos(1/x);
  t = a:gap:b;
  n = length(t);
  y = zeros(1,n);
  for k=1:n
		y(k) = f(t(k));
	end
	plot(t,y);
end