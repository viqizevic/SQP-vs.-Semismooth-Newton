% A function as an example for the thesis.
% Based on an example in [Alt11].
% See file tex/images/beispiel-unrestr-opt-prob.table
% for more information.
% 
function y = example_func_1(x)
	n = length(x);
	y = zeros(n,1);
	for k=1:n
		y(k) = (2*x(k)-2)^2*(3*x(k)+3)^2 + 10*x(k);
	end
end