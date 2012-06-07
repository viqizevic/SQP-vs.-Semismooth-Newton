%problem_v_bazaraa_shetty
%(Bazaraa-Shetty-Funktion, vgl. Beispiel 1.4.3 in \cite[S.~15f]{alt})
%\min_{x\in\R^2}\ (x_1-2)^4+(x_1-2 x_2)^2 
%\nb -10 \leq x_i & \leq 10,\ i = 1,2 \\
%
function y = func_for_problem_v_bazaraa_shetty(x)
	y = (x(1)-2)^4+(x(1)-2*x(2))^2;
end