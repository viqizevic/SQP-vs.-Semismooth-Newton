% Computes the increment with the method from Armijo

function sigma = armijo_schrittweite(f, gradf, x, d, delta, gamma, beta)

sigma = -(gamma/(norm(d)^2))*feval(gradf,x)'*d;

while( true )

    if( feval(f,x+sigma*d) <= feval(f,x)+delta*sigma*feval(gradf,x)'*d )
        break; % stop
    end

    % make sigma smaller
    sigma = beta * sigma;

end

