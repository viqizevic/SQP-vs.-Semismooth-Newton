function gradf = approx_gradient(f,x,epsi)
    n = length(x);
    gradf = zeros(n,1);
    for k=1:n
        ek = zeros(n,1);
        ek(k) = 1;
        gradf(k) = ( feval(f,x+epsi*ek) - feval(f,x-epsi*ek) ) / (2*epsi);
    end
end