String.prototype.escapeHTML = function () {
    return this.replace(/&/g,'&amp;').replace(/>/g,'&gt;').replace(/</g,'&lt;').replace(/'/g,'&#x27;').replace(/"/g,'&quot;');
};
